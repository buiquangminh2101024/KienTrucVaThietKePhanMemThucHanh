package iuh.fit.se.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {
    private String id;
    private String name;
    private String lecturer;
}
