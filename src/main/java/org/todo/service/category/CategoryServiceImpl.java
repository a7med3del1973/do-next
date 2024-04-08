package org.todo.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.todo.entity.Category;
import org.todo.entity.User;
import org.todo.exception.TodoException;
import org.todo.mapper.CategoryMapper;
import org.todo.mapper.TaskMapper;
import org.todo.model.category.response.CategoryResponse;
import org.todo.model.task.response.TaskResponse;
import org.todo.repository.CategoryRepository;
import org.todo.service.user.UserService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final CategoryMapper categoryMapper;
    private final TaskMapper taskMapper;

    @Autowired
    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            UserService userService,
            CategoryMapper categoryMapper,
            TaskMapper taskMapper) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.categoryMapper = categoryMapper;
        this.taskMapper = taskMapper;
    }


    @Override
    public Category getCategory(String name, String email) {
        return categoryRepository.findByNameAndUser(name.toLowerCase(), userService.getUser(email))
                .orElseThrow(
                        () -> new TodoException("Category not found", HttpStatus.NOT_FOUND)
                );
    }

    @Override
    public Category getCategory(Long id, String email) {
        return categoryRepository.findByIdAndUser(id, userService.getUser(email))
                .orElseThrow(
                        () -> new TodoException("Category not found", HttpStatus.NOT_FOUND)
                );
    }

    @Override
    public CategoryResponse createCategory(String name, String email) {
        User user = userService.getUser(email);
        if (categoryRepository.existsByNameIgnoreCaseAndUser(name.toLowerCase(), user)) {
            throw new TodoException("Category already exists", HttpStatus.BAD_REQUEST);
        }
        return categoryMapper.toCategoryResponse(
                categoryRepository.save(Category.builder()
                        .name(name.toLowerCase())
                        .user(user)
                        .build())
        );
    }

    @Override
    public CategoryResponse findCategory(Long id, String email) {
        return categoryMapper.toCategoryResponse(getCategory(id, email));
    }

    @Override
    public void deleteCategory(String name, String email) {
        Category category = getCategory(name, email);
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryResponse> getCategories(String email) {
        return categoryMapper.toCategoryResponseList(
                categoryRepository.findByUser(userService.getUser(email))
        );
    }

    @Override
    public List<TaskResponse> getTasks(String name, String email) {
        return taskMapper.toTaskResponseList(getCategory(name, email).getTasks());
    }
}
