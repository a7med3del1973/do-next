package org.todo.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todo.entity.Task;
import org.todo.entity.User;
import org.todo.exception.TodoException;
import org.todo.mapper.TaskMapper;
import org.todo.model.task.request.TaskRequest;
import org.todo.model.task.response.TaskResponse;
import org.todo.repository.TaskRepository;
import org.todo.service.category.CategoryService;
import org.todo.service.user.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;
    private final CategoryService categoryService;

    @Autowired
    public TaskServiceImpl(
            TaskRepository taskRepository,
            TaskMapper taskMapper,
            UserService userService,
            CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userService = userService;
        this.categoryService = categoryService;
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

    private Task getSpecificTask(Long id, String email) {
        User user = userService.getUser(email);
        return taskRepository.findByUserAndId(user, id).orElseThrow(
                () -> new TodoException("Task not found", HttpStatus.NOT_FOUND)
        );
    }


    @Override
    public TaskResponse getTask(Long id, String email) {
        return taskMapper.toTaskResponse(getSpecificTask(id, email));
    }

    @Override
    @Transactional
    public TaskResponse addTask(TaskRequest task, String email) {
        User user = userService.getUser(email);
        Task newTask = taskMapper.toTask(task, user, LocalDate.now());
        if (task.getDueDate().isBefore(LocalDate.now()))
            throw new TodoException("Due date must be in the future", HttpStatus.BAD_REQUEST);
        System.out.println(newTask);
        if (task.getCategoryId() != null && task.getCategoryId() != 0) {
            newTask.setCategory(categoryService.getCategory(task.getCategoryId(), email));
        }
        newTask = taskRepository.save(newTask);
        return taskMapper.toTaskResponse(newTask);
    }

    @Override
    public TaskResponse updateTask(Long id, String email, TaskRequest task) {
        User user = userService.getUser(email);
        Task existingTask = taskRepository.findByUserAndId(user, id).orElseThrow(
                () -> new TodoException("Task not found", HttpStatus.NOT_FOUND)
        );
        if (task.getDueDate().isBefore(LocalDate.now()))
            throw new TodoException("Due date must be in the future", HttpStatus.BAD_REQUEST);
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        if (task.getCategoryId() != null) {
            existingTask.setCategory(categoryService.getCategory(task.getCategoryId(), email));
        }
        return taskMapper.toTaskResponse(taskRepository.save(existingTask));
    }

    @Override
    public void addToCategory(Long id, Long catId, String email) {
        Task task = getSpecificTask(id, email);
        task.setCategory(categoryService.getCategory(catId, email));
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id, String email) {
        Task task = getSpecificTask(id, email);
        taskRepository.delete(task);
    }

    @Override
    public void toggleComplete(Long id, String email) {
        Task task = getSpecificTask(id, email);
        task.setCompleted(!task.isCompleted());
        task.setCompletedDate(task.isCompleted() ? LocalDate.now() : null);
        taskRepository.save(task);
    }
}
