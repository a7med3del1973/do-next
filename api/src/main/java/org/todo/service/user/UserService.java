package org.todo.service.user;

import org.todo.entity.User;

public interface UserService {
    void save(User user);
    boolean existsByEmail(String email);
    User getUser(Long id);
    User getUser(String email);
    User updateUser(Long id, User user);
    void deleteUser(Long id);

}
