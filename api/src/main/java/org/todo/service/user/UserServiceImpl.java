package org.todo.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.todo.entity.User;
import org.todo.exception.TodoException;
import org.todo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new TodoException("User not found", HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new TodoException("User not found", HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public User updateUser(Long id, User user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new TodoException("User not found", HttpStatus.NOT_FOUND)
        );
        userRepository.delete(user);
    }
}
