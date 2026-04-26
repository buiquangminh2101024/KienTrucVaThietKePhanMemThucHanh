import dotenv from 'dotenv';
dotenv.config();

export const config = {
    port: process.env.PORT || 3000,
    mariaDb: {
        host: process.env.MARIADB_HOST || 'localhost',
        user: process.env.MARIADB_USER || 'cat_user',
        password: process.env.MARIADB_PASSWORD || 'cat_password',
        database: process.env.MARIADB_DATABASE || 'cat_db',
    },
    redisUrl: process.env.REDIS_URL || 'redis://localhost:6379',
    rabbitMqUrl: process.env.RABBITMQ_URL || 'amqp://localhost:5672'
};
