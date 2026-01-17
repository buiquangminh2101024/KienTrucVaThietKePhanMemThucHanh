package iuh.fit.se.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String artist;
    private String singer;
    private String description;
    private Role forUser;

    public Song(String title, String artist, String singer, String description, Role forUser) {
        this.title = title;
        this.artist = artist;
        this.singer = singer;
        this.description = description;
        this.forUser = forUser;
    }
}
