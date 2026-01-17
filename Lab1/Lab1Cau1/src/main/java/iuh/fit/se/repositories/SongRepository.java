package iuh.fit.se.repositories;

import iuh.fit.se.entities.Role;
import iuh.fit.se.entities.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {
    Page<Song> findByForUser(Role forUser, Pageable pageable);
}
