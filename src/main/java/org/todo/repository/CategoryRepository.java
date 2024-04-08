package org.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.todo.entity.Category;
import org.todo.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    Optional<Category> findByNameAndUser(String name, User user);
    Optional<Category> findByIdAndUser(Long id, User user);
    boolean existsByNameIgnoreCaseAndUser(String name, User user);
    List<Category> findByUser(User user);
}
