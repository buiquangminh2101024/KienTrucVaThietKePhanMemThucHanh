## lab5_producer.py – Gửi events lên Kafka
from kafka import KafkaProducer
import json, time, random

producer = KafkaProducer(
    bootstrap_servers=['localhost:9092'],
    value_serializer=lambda v: json.dumps(v).encode('utf-8'),
    key_serializer=lambda k: k.encode('utf-8') if k else None
)

def simulate_orders(count: int = 20):
    users = ['user_001', 'user_002', 'user_003']
    
    for i in range(count):
        order = {
            'order_id': f'ORD-{1000 + i}',
            'user_id': random.choice(users),
            'amount': random.randint(50000, 5000000),
            'timestamp': time.time()
        }
        
        # Key = user_id → đảm bảo cùng user vào cùng partition
        future = producer.send(
            topic='order-events',
            key=order['user_id'],
            value=order
        )
        
        record = future.get(timeout=10)
        print(f"Sent: {order['order_id']} → Partition {record.partition}, Offset {record.offset}")
        time.sleep(0.5)

simulate_orders(20)
producer.close()