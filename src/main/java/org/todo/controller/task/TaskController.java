package org.todo.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todo.model.response.Response;
import org.todo.model.task.request.TaskRequest;
import org.todo.service.task.TaskService;

import java.security.Principal;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Response> getTasks(Principal connectedUser) {
        Response response = Response.builder()
                .success(true)
                .data(taskService.getTasks(connectedUser.getName()))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getTask(Principal connectedUser, @PathVariable Long id) {
        Response response = Response.builder()
                .success(true)
                .data(taskService.getTask(id, connectedUser.getName()))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchTasks(Principal connectedUser, @RequestParam String query) {
        Response response = Response.builder()
                .success(true)
                .data(taskService.searchTasks(connectedUser.getName(), query))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response> addTask(Principal connectedUser, @RequestBody TaskRequest task) {
        Response response = Response.builder()
                .success(true)
                .data(taskService.addTask(task, connectedUser.getName()))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateTask(Principal connectedUser, @PathVariable Long id, @RequestBody TaskRequest task) {
        Response response = Response.builder()
                .success(true)
                .data(taskService.updateTask(id, connectedUser.getName(), task))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteTask(Principal connectedUser, @PathVariable Long id) {
        taskService.deleteTask(id, connectedUser.getName());
        Response response = Response.builder()
                .success(true)
                .data("Task deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/toggle-complete/{id}")
    public ResponseEntity<Response> toggleComplete(Principal connectedUser, @PathVariable Long id) {
        taskService.toggleComplete(id, connectedUser.getName());
        Response response = Response.builder()
                .success(true)
                .data("Task toggled successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
