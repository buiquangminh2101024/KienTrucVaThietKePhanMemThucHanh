package iuh.fit.se.services.impl;

import iuh.fit.se.entities.User;
import iuh.fit.se.repositories.UserRepository;
import iuh.fit.se.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username).getFirst();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
