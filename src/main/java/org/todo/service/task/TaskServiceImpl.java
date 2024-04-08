package org.todo.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todo.entity.Task;
import org.todo.entity.User;
import org.todo.exception.TodoException;
import org.todo.mapper.CategoryMapper;
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
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @Autowired
    public TaskServiceImpl(
            TaskRepository taskRepository,
            TaskMapper taskMapper,
            CategoryMapper categoryMapper,
            UserService userService,
            CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.categoryMapper = categoryMapper;
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
        if (task.getCategoryId() != null) {
            newTask.setCategory(categoryMapper.toCategory(
                    categoryService.findCategory(task.getCategoryId(), email))
            );
        }
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
    }
}
