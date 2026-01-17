package iuh.fit.se.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${app.rabbitmq.song-queue}")
    private String queueName;

    @Bean
    public Queue songQueue() {
        return new Queue(queueName);
    }

    // Tự động chuyển đổi Object sang JSON khi gửi/nhận
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
