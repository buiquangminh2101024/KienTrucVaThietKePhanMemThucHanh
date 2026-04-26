import { createPool } from 'mariadb';
import { config } from './config';

export const pool = createPool({
    host: config.mariaDb.host,
    user: config.mariaDb.user,
    password: config.mariaDb.password,
    database: config.mariaDb.database,
    connectionLimit: 5
});

export const initDb = async () => {
    let conn;
    try {
        conn = await pool.getConnection();
        await conn.query(`
            CREATE TABLE IF NOT EXISTS cats (
                id VARCHAR(255) PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                age INT NOT NULL,
                breed VARCHAR(255)
            )
        `);
        console.log('Database initialized');
    } catch (err) {
        console.error('Error initializing db', err);
    } finally {
        if (conn) conn.release();
    }
};
