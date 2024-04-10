package org.todo.model.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Response {
    private boolean success;
    private Object data;
    private Object error;
}
