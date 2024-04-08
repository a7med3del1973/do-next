package org.todo.model.task.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskRequest {
    private String title;
    private String description;
    private boolean completed;
    private LocalDate dueDate;
    @Nullable
    private Long categoryId;
}
