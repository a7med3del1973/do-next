package org.todo.service.token;

import org.todo.entity.Token;

public interface TokenService {
    Token findByToken(String token);
    String generateToken();
    void saveToken(Token token);
    void deleteToken(Token token);
}
