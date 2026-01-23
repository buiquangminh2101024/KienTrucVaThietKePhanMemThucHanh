package iuh.fit.se.entities;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MusicProduct implements Serializable {
    private Long id;
    private String title;
    private String artist;
    private double price;
}
