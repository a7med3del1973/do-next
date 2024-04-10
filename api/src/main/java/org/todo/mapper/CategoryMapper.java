package org.todo.mapper;

import org.mapstruct.Mapper;
import org.todo.entity.Category;
import org.todo.model.category.response.CategoryResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
}
