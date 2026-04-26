import pika
import json

connection = pika.BlockingConnection(
    pika.ConnectionParameters(
        host='localhost',
        credentials=pika.PlainCredentials('guest', 'guest')
    )
)
channel = connection.channel()

channel.basic_publish(
    exchange='movie_ticket_events',
    routing_key='BOOKING_CREATED',       # Tên queue = routing key
    body=json.dumps(
        {
          "type": "BOOKING_CREATED",
          "bookingId": "BK123",
          "userId": "U01",
          "movieId": "M009",
          "seats": ["A1", "A2"],
          "totalPrice": 180000,
          "createdAt": "2026-04-17T10:20:00.000Z"
        }
    ),
    properties=pika.BasicProperties(delivery_mode=1)
)
connection.close()