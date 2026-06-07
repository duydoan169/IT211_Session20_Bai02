package org.example.bai2.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "token_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token_value", columnDefinition = "TEXT")
    private String refreshTokenValue;

    @Column(name = "is_revoked")
    private boolean revoked;

    @Column(name = "is_expired")
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}