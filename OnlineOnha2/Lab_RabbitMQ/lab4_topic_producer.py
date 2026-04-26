## lab4_topic_producer.py – Gửi event theo topic
import pika, json, datetime

connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel()
channel.exchange_declare(exchange='events', exchange_type='topic', durable=True)

events = [
    ('orders.created.vn',   {'order_id': 'ORD-1', 'country': 'VN'}),
    ('orders.shipped.vn',   {'order_id': 'ORD-2', 'country': 'VN'}),
    ('orders.created.sg',   {'order_id': 'ORD-3', 'country': 'SG'}),
    ('payments.success.vn',  {'payment_id': 'PAY-1', 'amount': 500000}),
    ('payments.failed.vn',   {'payment_id': 'PAY-2', 'reason': 'insufficient funds'}),
]

for routing_key, data in events:
    channel.basic_publish(
        exchange='events',
        routing_key=routing_key,
        body=json.dumps(data)
    )
    print(f"Published [{routing_key}]: {data}")

connection.close()