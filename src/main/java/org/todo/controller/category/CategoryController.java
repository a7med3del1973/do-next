package org.todo.controller.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todo.model.category.request.CategoryRequest;
import org.todo.model.response.Response;
import org.todo.service.category.CategoryService;

import java.security.Principal;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Response> getCategories(Principal connectedUser) {
        Response response = Response.builder()
                .success(true)
                .data(categoryService.getCategories(connectedUser.getName()))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tasks/{catName}")
    public ResponseEntity<Response> getTasks(Principal connectedUser, @PathVariable String catName) {
        Response response = Response.builder()
                .success(true)
                .data(categoryService.getTasks(catName, connectedUser.getName()))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response> addCategory(Principal connectedUser, @RequestBody CategoryRequest category) {
        Response response = Response.builder()
                .success(true)
                .data(categoryService.createCategory(category.getName(), connectedUser.getName()))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{catName}")
    public ResponseEntity<Response> deleteCategory(Principal connectedUser, @PathVariable String catName) {
        categoryService.deleteCategory(catName, connectedUser.getName());
        return ResponseEntity.ok(Response.builder().success(true).data(true).build());
    }

}
