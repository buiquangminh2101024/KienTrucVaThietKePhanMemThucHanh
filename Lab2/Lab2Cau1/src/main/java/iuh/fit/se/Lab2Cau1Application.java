package iuh.fit.se;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Lab2Cau1Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab2Cau1Application.class, args);
    }

}
