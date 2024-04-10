package org.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.todo.entity.Task;
import org.todo.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByUserIdAndId(Long userId, Long id);
    Optional<Task> findByUserAndId(User user, Long id);
    List<Task> findByUserAndTitleContainingOrDescriptionContaining(User user, String title, String description);
}
