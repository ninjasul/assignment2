package com.assignment.support.entity;

import com.assignment.support.dto.AccountDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.assignment.support.entity.AccountRole.USER;

@NoArgsConstructor
@Getter
@Entity
@Builder
public class Account  {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    public Account(Long id, String username, String password, AccountRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Account(String username, String password, AccountRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static Account of(AccountDto accountDto, PasswordEncoder passwordEncoder) {
        return new Account().builder()
                        .username(accountDto.getUsername())
                        .password(passwordEncoder.encode(accountDto.getPassword()))
                        .role(USER)
                        .build();
    }

    public List<String> getRoles() {
        return Stream.of(role)
                .map(AccountRole::name)
                .collect(Collectors.toList());
    }
}