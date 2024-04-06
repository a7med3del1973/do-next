package org.todo.service.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.todo.entity.Token;
import org.todo.exception.TodoException;
import org.todo.repository.TokenRepository;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token findByToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(
                        () -> new TodoException("Invalid token", HttpStatus.NOT_FOUND)
                );
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString() + '-' + UUID.randomUUID();
    }

    @Override
    public void saveToken(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public void deleteToken(Token token) {
        tokenRepository.delete(token);
    }
}
