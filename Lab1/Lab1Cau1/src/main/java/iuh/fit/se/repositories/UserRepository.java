package iuh.fit.se.repositories;

import iuh.fit.se.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUserName(String userName);
}
