package iuh.fit.se.services;

import iuh.fit.se.entities.Role;
import iuh.fit.se.entities.Song;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SongService {
    Song findById(int id);
    List<Song> findAll();
    Page<Song> findAll(int page, int size);
    Page<Song> findByForUser(Role role, int page, int size);
    long count();
    Song save(Song song);
}
