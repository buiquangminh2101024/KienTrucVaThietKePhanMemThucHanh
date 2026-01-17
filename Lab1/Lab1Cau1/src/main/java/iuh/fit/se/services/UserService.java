package iuh.fit.se.services;

import iuh.fit.se.entities.User;

public interface UserService {
    User findByUserName(String username);
    User findById(int id);
    long count();
    User save(User user);
}
