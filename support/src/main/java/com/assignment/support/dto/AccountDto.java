package com.assignment.support.dto;

import com.assignment.support.entity.Account;
import com.assignment.support.entity.Support;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Builder
public class AccountDto {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public AccountDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static AccountDto of(Account account) {
        return new AccountDto().builder()
                .username(account.getUsername())
                .build();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountDto{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}