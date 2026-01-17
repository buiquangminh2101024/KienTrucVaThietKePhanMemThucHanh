package iuh.fit.se.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LoginRequest {
    private String userName;
    private String password;
}
