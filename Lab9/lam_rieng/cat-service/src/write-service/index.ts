import { connectRabbitMQ, channel } from '../shared/rabbitmq';
import { pool } from '../shared/db';

const startWriteService = async () => {
    await connectRabbitMQ();
    
    channel.consume('write-mq', async (msg) => {
        if (msg) {
            try {
                const event = JSON.parse(msg.content.toString());
                
                if (event.action === 'CREATE') {
                    const conn = await pool.getConnection();
                    await conn.query(
                        'INSERT INTO cats (id, name, age, breed) VALUES (?, ?, ?, ?)',
                        [event.id, event.name, event.age, event.breed]
                    );
                    conn.release();
                    console.log(`Saved cat ${event.id} to DB`);
                }
                
                channel.ack(msg);
            } catch (err) {
                console.error('Error processing write event', err);
                channel.nack(msg);
            }
        }
    });
    
    console.log('Write Service listening on write-mq');
};

startWriteService();
