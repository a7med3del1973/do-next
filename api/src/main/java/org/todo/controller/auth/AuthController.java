package org.todo.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.todo.model.auth.request.LoginRequest;
import org.todo.model.auth.request.RegisterRequest;
import org.todo.model.response.Response;
import org.todo.service.auth.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = Response.builder()
                .success(true)
                .data(authService.login(loginRequest))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest registerRequest) {
        Response response = Response.builder()
                .success(true)
                .data(authService.register(registerRequest))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Response> verifyEmail(@RequestParam String token) {
        Response response = Response.builder()
                .success(true)
                .data(authService.verifyEmail(token))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody String token) {
        authService.logout(token);
    }
}
