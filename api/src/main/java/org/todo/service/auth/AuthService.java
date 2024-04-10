package org.todo.service.auth;

import org.todo.model.auth.request.LoginRequest;
import org.todo.model.auth.request.RegisterRequest;
import org.todo.model.auth.response.LoginResponse;
import org.todo.model.auth.response.RegisterResponse;
import org.todo.model.auth.response.VerifyEmailResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    void logout(String token);
    VerifyEmailResponse verifyEmail(String token);
}
