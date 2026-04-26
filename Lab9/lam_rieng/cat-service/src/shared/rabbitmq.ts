import amqp from 'amqplib';
import { config } from './config';

export let channel: amqp.Channel;

export const connectRabbitMQ = async () => {
    try {
        const connection = await amqp.connect(config.rabbitMqUrl);
        channel = await connection.createChannel();
        await channel.assertQueue('write-mq', { durable: true });
        await channel.assertQueue('read-mq', { durable: true });
        await channel.assertQueue('cache-update-mq', { durable: true });
        console.log('Connected to RabbitMQ');
    } catch (err) {
        console.error('RabbitMQ Connection Error', err);
    }
};
