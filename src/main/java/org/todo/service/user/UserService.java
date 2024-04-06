package org.todo.service.user;

import org.todo.entity.User;

public interface UserService {
    User getUser(Long id);
    User getUser(String email);
    User updateUser(Long id, User user);
    void deleteUser(Long id);

}
