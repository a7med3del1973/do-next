package org.todo.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todo.config.security.JwtService;
import org.todo.entity.Token;
import org.todo.entity.User;
import org.todo.enums.Role;
import org.todo.enums.TokenType;
import org.todo.exception.TodoException;
import org.todo.model.auth.request.LoginRequest;
import org.todo.model.auth.request.RegisterRequest;
import org.todo.model.auth.response.LoginResponse;
import org.todo.model.auth.response.RegisterResponse;
import org.todo.model.auth.response.VerifyEmailResponse;
import org.todo.service.email.EmailService;
import org.todo.service.token.TokenService;
import org.todo.service.user.UserService;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(
            UserService userService,
            JwtService jwtService,
            EmailService emailService,
            TokenService tokenService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        boolean isUserExists = userService.existsByEmail(registerRequest.getEmail());
        if (isUserExists) {
            throw new TodoException("User already exists", HttpStatus.BAD_REQUEST);
        }
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .isVerified(false)
                .build();
        userService.save(user);

        String verificationToken = tokenService.generateToken();
        tokenService.saveToken(
                Token.builder()
                        .token(verificationToken)
                        .user(user)
                        .tokenType(TokenType.EMAIL_VERIFICATION)
                        .build()
        );
        String emailContent = "<div style='font-family: Arial, sans-serif; width: 80%; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;'>"
                + "<div style='text-align: center; padding: 10px; background-color: #f8f8f8; border-bottom: 1px solid #ddd;'>"
                + "<h1>Welcome to Todo</h1>"
                + "</div>"
                + "<div style='padding: 20px;'>"
                + "<p>Dear " + user.getFirstName() + ",</p>"
                + "<p>Thank you for registering at Todo. Please click the link below to verify your email:</p>"
                + "<p><a href='http://localhost:8080/auth/verify-email?token=" + verificationToken + "&redirect=" + user.getIsVerified() + "'>Verify Email</a></p>"
                + "<p>If you did not register at Todo, please ignore this email.</p>"
                + "<p>Best Regards,</p>"
                + "<p>Todo Team</p>"
                + "</div>"
                + "</div>";
        emailService.send(user.getEmail() , "Email Verification" , emailContent);

        return RegisterResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("User registered successfully, please verify your email")
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail() , loginRequest.getPassword())
            );
        } catch (Exception e) {
            throw new TodoException("Username or password is incorrect" , HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getUser(loginRequest.getEmail());
        if (!user.getIsVerified()) {
            throw new TodoException("User is not verified, please verify your email", HttpStatus.UNAUTHORIZED);
        }
        String token = jwtService.generateToken(user);
        return LoginResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .token(token)
                .build();
    }

    @Override
    public void logout(String token) {
        // TODO
    }

    @Override
    public VerifyEmailResponse verifyEmail(String token) {
        Token emailVerificationToken = tokenService.findByToken(token);
        User user = emailVerificationToken.getUser();
        user.setIsVerified(true);
        userService.save(user);
        tokenService.deleteToken(emailVerificationToken);
        return VerifyEmailResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Email verified successfully")
                .build();
    }
}
