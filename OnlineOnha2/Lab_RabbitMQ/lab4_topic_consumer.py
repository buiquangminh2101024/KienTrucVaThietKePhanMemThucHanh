## lab4_topic_consumer.py – Subscribe theo pattern
import pika, json, sys

# Nhận pattern từ argument: "orders.#", "*.created.*", "#"
binding_key = sys.argv[1] if len(sys.argv) > 1 else "#"

connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel()
channel.exchange_declare(exchange='events', exchange_type='topic', durable=True)

result = channel.queue_declare(queue='', exclusive=True)
queue_name = result.method.queue
channel.queue_bind(exchange='events', queue=queue_name, routing_key=binding_key)

print(f"[*] Subscribed to pattern: '{binding_key}'")

def on_event(ch, method, properties, body):
    data = json.loads(body)
    print(f"  → [{method.routing_key}] {data}")

channel.basic_consume(queue=queue_name, on_message_callback=on_event, auto_ack=True)
channel.start_consuming()