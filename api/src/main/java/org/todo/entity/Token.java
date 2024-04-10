package org.todo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.todo.enums.TokenType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;
    private LocalDateTime expiredAt;
    private LocalDateTime verifiedAt;
}