package iuh.fit.se.controllers;

import iuh.fit.se.entities.Role;
import iuh.fit.se.entities.Song;
import iuh.fit.se.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SongMessageListener {
    private final SongService songService;

    @RabbitListener(queues = "${app.rabbitmq.song-queue}")
    public Map<String, Object> handleSongRequest(Map<String, Object> request) {
        String role = (String) request.get("role");
        int page = (int) request.get("page");
        int size = (int) request.get("size");

        Page<Song> songPage;
        if ("ADMIN".equals(role)) {
            songPage = (Page<Song>) songService.findAll(page, size); // Giả sử service trả về Page
        } else {
            songPage = (Page<Song>) songService.findByForUser(Role.NORMAL, page, size);
        }

        return Map.of(
                "content", songPage.getContent(),
                "totalPages", songPage.getTotalPages(),
                "totalElements", songPage.getTotalElements(),
                "number", songPage.getNumber()
        );
    }
}
