package org.todo.service.task;

import org.todo.model.task.request.TaskRequest;
import org.todo.model.task.response.TaskResponse;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getTasks(String email);
    List<TaskResponse> searchTasks(String email, String search);
    TaskResponse getTask(Long id, String email);
    TaskResponse addTask(TaskRequest task, String email);
    TaskResponse updateTask(Long id, String email, TaskRequest task);
    void deleteTask(Long id, String email);
    void toggleComplete(Long id, String email);
}
