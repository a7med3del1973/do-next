package org.todo.model.category.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
}
