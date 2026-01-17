package iuh.fit.se.configs;

import iuh.fit.se.entities.Role;
import iuh.fit.se.entities.Song;
import iuh.fit.se.entities.User;
import iuh.fit.se.services.SongService;
import iuh.fit.se.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class InitConfig {
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner runner(UserService userService, SongService songService) {
        return args -> {
            if (userService.count() + songService.count() > 0) return;

            userService.save(new User("admin1", passwordEncoder.encode("admin1"), Role.VIP));
            userService.save(new User("admin2", passwordEncoder.encode("admin2"), Role.VIP));
            userService.save(new User("user1", passwordEncoder.encode("user1"), Role.NORMAL));
            userService.save(new User("user2", passwordEncoder.encode("user2"), Role.NORMAL));

            songService.save(new Song("Cánh thiệp đầu xuân", "Minh Kỳ và Lê Dinh", "Như Quỳnh và Thế Sơn", "", Role.VIP));
            songService.save(new Song("Xuân Đã Về", "Minh Kỳ", "Thanh Lan", "", Role.VIP));
            songService.save(new Song("Xuân Đẹp Làm Sao", "Thanh Sơn", "Như Quỳnh", "", Role.VIP));
            songService.save(new Song("Năm Qua Đã Làm Gì", "Bùi Công Nam", "Noo Phước Thịnh", "", Role.NORMAL));
            songService.save(new Song("Tết đoàn viên", "Tạ Duy Tuấn", "Đan Trường và Cẩm Ly", "", Role.NORMAL));
        };
    }
}
