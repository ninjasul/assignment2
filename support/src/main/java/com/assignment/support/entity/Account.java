package com.assignment.support.entity;

import com.assignment.support.dto.AccountDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
@Builder
public class Account  {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String role;

    public Account(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static Account of(AccountDto accountDto, PasswordEncoder passwordEncoder) {
        return new Account().builder()
                        .username(accountDto.getUsername())
                        .password(passwordEncoder.encode(accountDto.getPassword()))
                        .role("ROLE_USER")
                        .build();
    }
}