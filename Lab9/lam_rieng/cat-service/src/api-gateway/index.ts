import express from 'express';
import NodeCache from 'node-cache';
import { v4 as uuidv4 } from 'uuid';
import { connectRabbitMQ, channel } from '../shared/rabbitmq';
import { connectRedis, redisClient } from '../shared/redis';
import { catBloomFilter } from '../shared/bloom-filter';
import { initDb } from '../shared/db';

const app = express();
app.use(express.json());

// Local cache: TTL 60 seconds
const localCache = new NodeCache({ stdTTL: 60 });

app.post('/cats', async (req, res) => {
    try {
        const { name, age, breed } = req.body;
        const id = uuidv4();
        
        const event = { id, name, age, breed, action: 'CREATE' };
        
        // Add to Bloom Filter
        catBloomFilter.add(id);

        // Write to Redis first (Synchronous for fast read availability)
        const catData = { id, name, age, breed };
        await redisClient.setEx(`cat:${id}`, 300, JSON.stringify(catData)); // 5 mins TTL
        localCache.set(id, catData); // Also save to local cache for faster reads
        
        // Send to write-mq for async DB insertion
        channel.sendToQueue('write-mq', Buffer.from(JSON.stringify(event)));
        
        res.status(202).json({ message: 'Create request accepted (Saved to Redis)', id });
    } catch (error) {
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

app.get('/cats/:id', async (req, res) => {
    try {
        const { id } = req.params;

        // 1. Check Bloom Filter to prevent Cache Penetration
        if (!catBloomFilter.has(id)) {
            return res.status(404).json({ error: 'Cat not found (Bloom Filter)' });
        }

        // 2. Check Local Cache
        const localData = localCache.get(id);
        if (localData === 'NULL') {
            return res.status(404).json({ error: 'Cat not found (Local Cache Null)' });
        }
        if (localData) {
            console.log('Cache Hit: Local Cache');
            return res.json(localData);
        }

        // 3. Check Redis Cache
        const redisData = await redisClient.get(`cat:${id}`);
        if (redisData === 'NULL') {
            // Cache Null Value technique
            localCache.set(id, 'NULL', 30);
            return res.status(404).json({ error: 'Cat not found (Redis Null)' });
        }
        if (redisData) {
            console.log('Cache Hit: Redis');
            const parsedData = JSON.parse(redisData);
            localCache.set(id, parsedData);
            return res.json(parsedData);
        }

        // 4. Cache Miss -> Event-Driven via read-mq
        console.log('Cache Miss: Sending to read-mq');
        
        // We can wait for the response via a correlationId, but to keep it simple Event-Driven,
        // we can either return "Processing" or wait using a temporary queue (RPC pattern).
        // Let's implement RPC pattern over RabbitMQ for the read path.
        
        const correlationId = uuidv4();
        const replyQueue = await channel.assertQueue('', { exclusive: true });
        
        channel.sendToQueue('read-mq', Buffer.from(JSON.stringify({ id })), {
            correlationId,
            replyTo: replyQueue.queue
        });
        
        // Wait for response
        channel.consume(replyQueue.queue, (msg) => {
            if (msg && msg.properties.correlationId === correlationId) {
                const response = JSON.parse(msg.content.toString());
                
                if (!response.found) {
                    // Save Null Value to prevent penetration
                    localCache.set(id, 'NULL', 30);
                    // The cache-updater will handle saving NULL to Redis
                    res.status(404).json({ error: 'Cat not found (DB)' });
                } else {
                    localCache.set(id, response.data);
                    res.json(response.data);
                }
                
                // Cleanup
                channel.deleteQueue(replyQueue.queue);
            }
        }, { noAck: true });

    } catch (error) {
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

const start = async () => {
    await initDb();
    await connectRedis();
    await connectRabbitMQ();
    app.listen(3000, () => {
        console.log('API Gateway listening on port 3000');
    });
};

start();
