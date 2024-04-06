package org.todo.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.todo.entity.Task;
import org.todo.entity.User;
import org.todo.exception.TodoException;
import org.todo.mapper.TaskMapper;
import org.todo.model.task.request.TaskRequest;
import org.todo.model.task.response.TaskResponse;
import org.todo.repository.TaskRepository;
import org.todo.service.user.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(
            TaskRepository taskRepository,
            TaskMapper taskMapper,
            UserService userService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userService = userService;
    }

    @Override
    public List<TaskResponse> getTasks(String email) {
        return taskMapper.toTaskResponseList(taskRepository.findAll());
    }

    @Override
    public List<TaskResponse> searchTasks(String email, String search) {
        User user = userService.getUser(email);
        return taskMapper.toTaskResponseList(
                taskRepository.findByUserAndTitleContainingOrDescriptionContaining(user, search, search)
        );
    }

    @Override
    public TaskResponse getTask(Long id, String email) {
        User user = userService.getUser(email);
        return taskMapper.toTaskResponse(
                taskRepository.findByUserAndId(user, id).orElseThrow(
                        () -> new TodoException("Task not found", HttpStatus.NOT_FOUND)
                )
        );
    }

    @Override
    public TaskResponse addTask(TaskRequest task, String email) {
        User user = userService.getUser(email);
        Task newTask = taskMapper.toTask(task, user, LocalDate.now());
        taskRepository.save(newTask);
        return taskMapper.toTaskResponse(newTask);
    }

    @Override
    public TaskResponse updateTask(Long id, String email, TaskRequest task) {
        User user = userService.getUser(email);
        Task existingTask = taskRepository.findByUserAndId(user, id).orElseThrow(
                () -> new TodoException("Task not found", HttpStatus.NOT_FOUND)
        );
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setCompleted(task.isCompleted());
        existingTask.setDueDate(task.getDueDate());
        return taskMapper.toTaskResponse(taskRepository.save(existingTask));
    }

    @Override
    public void deleteTask(Long id, String email) {
        User user = userService.getUser(email);
        Task task = taskRepository.findByUserAndId(user, id).orElseThrow(
                () -> new TodoException("Task not found", HttpStatus.NOT_FOUND)
        );
        taskRepository.delete(task);
    }

    @Override
    public void toggleComplete(Long id, String email) {
        User user = userService.getUser(email);
        Task task = taskRepository.findByUserAndId(user, id).orElseThrow(
                () -> new TodoException("Task not found", HttpStatus.NOT_FOUND)
        );
        task.setCompleted(!task.isCompleted());
    }
}
