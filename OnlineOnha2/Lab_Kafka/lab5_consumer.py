## lab5_consumer.py – Đọc events từ Kafka
from kafka import KafkaConsumer
import json, sys

group_id = sys.argv[1] if len(sys.argv) > 1 else 'analytics-group'
worker_id = sys.argv[2] if len(sys.argv) > 2 else 'C1'

consumer = KafkaConsumer(
    'order-events',
    bootstrap_servers=['localhost:9092'],
    group_id=group_id,
    auto_offset_reset='earliest',  # Đọc từ đầu nếu chưa có offset
    value_deserializer=lambda m: json.loads(m.decode('utf-8')),
    enable_auto_commit=True
)

print(f"[{worker_id}] Consumer group: {group_id} – listening...")

for message in consumer:
    order = message.value
    print(f"[{worker_id}] P{message.partition}@{message.offset}: "
          f"Order {order['order_id']} from {order['user_id']} – {order['amount']:,}đ")