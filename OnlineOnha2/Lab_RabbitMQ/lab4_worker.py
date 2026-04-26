## lab4_worker.py – Worker xử lý task (chạy nhiều instance)
import pika, time, sys

worker_id = sys.argv[1] if len(sys.argv) > 1 else "W1"

connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel()
channel.queue_declare(queue='task_queue', durable=True)
channel.basic_qos(prefetch_count=1)  # Fair dispatch

def process_task(ch, method, properties, body):
    task = body.decode()
    print(f"[{worker_id}] Processing: {task}")
    time.sleep(len(task) / 20)    # Giả lập thời gian xử lý
    print(f"[{worker_id}] Done: {task[:30]}...")
    ch.basic_ack(delivery_tag=method.delivery_tag)

channel.basic_consume(queue='task_queue', on_message_callback=process_task)
print(f'[{worker_id}] Ready. CTRL+C to exit')
channel.start_consuming()