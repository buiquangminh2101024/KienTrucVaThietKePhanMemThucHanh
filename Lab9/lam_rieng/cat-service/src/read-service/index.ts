import { connectRabbitMQ, channel } from '../shared/rabbitmq';
import { pool } from '../shared/db';

const startReadService = async () => {
    await connectRabbitMQ();
    
    channel.consume('read-mq', async (msg) => {
        if (msg) {
            try {
                const req = JSON.parse(msg.content.toString());
                const id = req.id;
                
                const conn = await pool.getConnection();
                const rows = await conn.query('SELECT * FROM cats WHERE id = ?', [id]);
                conn.release();
                
                let responseData;
                if (rows && rows.length > 0) {
                    responseData = { found: true, data: rows[0] };
                    // Tell cache-updater to update redis
                    channel.sendToQueue('cache-update-mq', Buffer.from(JSON.stringify({
                        id,
                        data: rows[0]
                    })));
                } else {
                    responseData = { found: false };
                    // Tell cache-updater to save NULL value to prevent penetration
                    channel.sendToQueue('cache-update-mq', Buffer.from(JSON.stringify({
                        id,
                        data: 'NULL'
                    })));
                }
                
                // Reply to RPC queue
                if (msg.properties.replyTo) {
                    channel.sendToQueue(
                        msg.properties.replyTo,
                        Buffer.from(JSON.stringify(responseData)),
                        { correlationId: msg.properties.correlationId }
                    );
                }
                
                channel.ack(msg);
            } catch (err) {
                console.error('Error processing read event', err);
                channel.nack(msg);
            }
        }
    });
    
    console.log('Read Service listening on read-mq');
};

startReadService();
