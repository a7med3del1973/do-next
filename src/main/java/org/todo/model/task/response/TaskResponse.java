package org.todo.model.task.response;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private LocalDate completedDate;
}
