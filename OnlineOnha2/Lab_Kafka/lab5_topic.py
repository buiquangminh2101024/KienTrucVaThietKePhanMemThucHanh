from kafka.admin import KafkaAdminClient, NewTopic
from kafka.errors import TopicAlreadyExistsError

def create_kafka_topic(bootstrap_servers, topic_name, num_partitions, replication_factor):
    # Khởi tạo Admin Client
    admin_client = KafkaAdminClient(
        bootstrap_servers=bootstrap_servers,
        client_id='admin_client_1'
    )

    # Định nghĩa topic mới
    topic_list = []
    new_topic = NewTopic(
        name=topic_name,
        num_partitions=num_partitions,
        replication_factor=replication_factor
    )
    topic_list.append(new_topic)

    try:
        # Gửi yêu cầu tạo topic
        admin_client.create_topics(new_topics=topic_list, validate_only=False)
        print(f"Topic '{topic_name}' created successfully.")
    except TopicAlreadyExistsError:
        print(f"Topic '{topic_name}' already exists.")
    except Exception as e:
        print(f"An error occurred: {e}")
    finally:
        # Đóng admin client
        admin_client.close()

# Cấu hình
BOOTSTRAP_SERVERS = ['localhost:9092'] # Thay đổi nếu Kafka chạy trên server khác
TOPIC_NAME = 'order-events'
NUM_PARTITIONS = 3
REPLICATION_FACTOR = 1 # Dùng 1 nếu chỉ có 1 broker

# Gọi hàm tạo
create_kafka_topic(BOOTSTRAP_SERVERS, TOPIC_NAME, NUM_PARTITIONS, REPLICATION_FACTOR)
