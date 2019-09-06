package com.assignment.support.init;

import com.assignment.support.entity.Account;
import com.assignment.support.entity.AccountRole;
import com.assignment.support.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.assignment.support.entity.AccountRole.ADMIN;

@Component
public class AppRunner implements ApplicationRunner {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        accountRepository.save(new Account().builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(ADMIN)
                .build()
        );
    }
}