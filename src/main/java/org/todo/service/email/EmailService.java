package org.todo.service.email;

public interface EmailService {
    void send(String to, String subject, String body);
}
