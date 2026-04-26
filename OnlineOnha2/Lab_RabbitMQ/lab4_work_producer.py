## lab4_work_producer.py – Gửi nhiều task
import pika, sys

connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel()
channel.queue_declare(queue='task_queue', durable=True)

messages = [
    "Task 1: Resize image product_001.jpg",
    "Task 2: Generate PDF invoice_2025.pdf",
    "Task 3: Send email to 1000 users",
    "Task 4: Update search index",
    "Task 5: Compress video tutorial.mp4",
]

for msg in messages:
    channel.basic_publish(
        exchange='',
        routing_key='task_queue',
        body=msg,
        properties=pika.BasicProperties(delivery_mode=2)
    )
    print(f" [x] Sent: {msg}")

connection.close()