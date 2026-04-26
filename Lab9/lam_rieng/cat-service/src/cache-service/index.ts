import { connectRabbitMQ, channel } from '../shared/rabbitmq';
import { connectRedis, redisClient } from '../shared/redis';

const startCacheUpdater = async () => {
    await connectRabbitMQ();
    await connectRedis();
    
    channel.consume('cache-update-mq', async (msg) => {
        if (msg) {
            try {
                const req = JSON.parse(msg.content.toString());
                const { id, data } = req;
                
                if (data === 'NULL') {
                    // Cache NULL value with short TTL (e.g., 30 seconds)
                    await redisClient.setEx(`cat:${id}`, 30, 'NULL');
                    console.log(`Updated Redis for ${id} with NULL (Cache Penetration protection)`);
                } else {
                    // Update cache with data, TTL 300 seconds (5 minutes) for automatic expiration
                    await redisClient.setEx(`cat:${id}`, 300, JSON.stringify(data));
                    console.log(`Updated Redis for cat:${id}`);
                }
                
                channel.ack(msg);
            } catch (err) {
                console.error('Error processing cache update', err);
                channel.nack(msg);
            }
        }
    });
    
    console.log('Cache Updater Service listening on cache-update-mq');
};

startCacheUpdater();
