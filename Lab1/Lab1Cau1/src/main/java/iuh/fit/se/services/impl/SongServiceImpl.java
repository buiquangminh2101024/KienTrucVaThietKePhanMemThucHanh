package iuh.fit.se.services.impl;

import iuh.fit.se.entities.Role;
import iuh.fit.se.entities.Song;
import iuh.fit.se.repositories.SongRepository;
import iuh.fit.se.services.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    @Override
    public Song findById(int id) {
        return songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song not found"));
    }

    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public Page<Song> findAll(int page, int size) {
        if ((page + 1) > Math.ceil((double) songRepository.count() / size)) {
            throw new IndexOutOfBoundsException("page size must be less than or equal to count");
        }
        Pageable pageable = PageRequest.of(page, size);
        return songRepository.findAll(pageable);
    }

    @Override
    public Page<Song> findByForUser(Role role, int page, int size) {
        if ((page + 1) > Math.ceil((double) songRepository.count() / size)) {
            throw new IndexOutOfBoundsException("page size must be less than or equal to count");
        }
        Pageable pageable = PageRequest.of(page, size);
        return songRepository.findByForUser(role, pageable);
    }

    @Override
    public long count() {
        return songRepository.count();
    }

    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }
}
