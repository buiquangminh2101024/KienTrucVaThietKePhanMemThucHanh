package iuh.fit.se.controllers;

import iuh.fit.se.entities.Role;
import iuh.fit.se.entities.Song;
import iuh.fit.se.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.song-queue}")
    private String queueName;

    @GetMapping("/normal")
    public Map<String, Object> getSongs_RoleNormal(@RequestParam int page, @RequestParam int size) {
        // Tạo một Map chứa tham số yêu cầu
        Map<String, Object> request = Map.of("role", "NORMAL", "page", page, "size", size);

        // Gửi qua RabbitMQ và đợi kết quả trả về
        return (Map<String, Object>) rabbitTemplate.convertSendAndReceive(queueName, request);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('VIP')")
    public Map<String, Object> getSongs_RoleAdmin(@RequestParam int page, @RequestParam int size) {
        Map<String, Object> request = Map.of("role", "ADMIN", "page", page, "size", size);
        return (Map<String, Object>) rabbitTemplate.convertSendAndReceive(queueName, request);
    }
}
