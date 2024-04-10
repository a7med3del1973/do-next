package org.todo.service.category;

import org.todo.entity.Category;
import org.todo.model.category.response.CategoryResponse;
import org.todo.model.task.response.TaskResponse;

import java.util.List;

public interface CategoryService {
    Category getCategory(String name, String email);
    Category getCategory(Long id, String email);
    CategoryResponse createCategory(String name, String email);
    CategoryResponse findCategory(Long id, String email);
    void deleteCategory(String name, String email);
    List<CategoryResponse> getCategories(String email);
    List<TaskResponse> getTasks(String name, String email);
}
