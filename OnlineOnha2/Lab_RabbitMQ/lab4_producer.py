## lab4_producer.py – Gửi message đơn giản
import pika

connection = pika.BlockingConnection(
    pika.ConnectionParameters(
        host='localhost',
        credentials=pika.PlainCredentials('guest', 'guest')
    )
)
channel = connection.channel()

# Tạo queue (idempotent – gọi nhiều lần không bị lỗi)
channel.queue_declare(queue='hello', durable=True)

channel.basic_publish(
    exchange='',               # Default exchange
    routing_key='hello',       # Tên queue = routing key
    body='Xin chào RabbitMQ!',
    properties=pika.BasicProperties(delivery_mode=2)
)
print(" [x] Sent 'Xin chào RabbitMQ!'")
connection.close()