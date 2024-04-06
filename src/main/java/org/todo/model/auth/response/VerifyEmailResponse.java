package org.todo.model.auth.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerifyEmailResponse {
    private int statusCode;
    private String message;
}
