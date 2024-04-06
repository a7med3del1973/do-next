package org.todo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.todo.entity.Task;
import org.todo.entity.User;
import org.todo.model.task.request.TaskRequest;
import org.todo.model.task.response.TaskResponse;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", source = "createdDate")
    Task toTask(TaskRequest taskRequest, User user, LocalDate createdDate);
    TaskResponse toTaskResponse(Task task);
    List<TaskResponse> toTaskResponseList(List<Task> tasks);
}
