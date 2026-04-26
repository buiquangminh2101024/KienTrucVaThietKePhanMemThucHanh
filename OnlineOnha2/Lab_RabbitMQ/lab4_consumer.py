## lab4_consumer.py – Nhận và xử lý message
import pika, time

connection = pika.BlockingConnection(
    pika.ConnectionParameters(
        host='localhost',
        credentials=pika.PlainCredentials('guest', 'guest')
    )
)
channel = connection.channel()
channel.queue_declare(queue='hello', durable=True)

# Prefetch count = 1: consumer chỉ nhận 1 message tại một thời điểm
channel.basic_qos(prefetch_count=1)

def callback(ch, method, properties, body):
    print(f" [x] Received: {body.decode()}")
    time.sleep(1)           # Giả lập xử lý
    ch.basic_ack(delivery_tag=method.delivery_tag)
    print(" [x] Done processing")

channel.basic_consume(queue='hello', on_message_callback=callback)
print(' [*] Waiting for messages. CTRL+C to exit')
channel.start_consuming()