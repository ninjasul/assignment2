package com.assignment.support.service;

import com.assignment.support.dto.AccountDto;
import com.assignment.support.entity.Account;
import com.assignment.support.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(accountRepository.findByUsernameAndPassword(username))
                .map(account -> User.builder()
                        .username(account.getUsername())
                        .password(passwordEncoder.encode(account.getPassword()))
                        .roles(account.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Account createNew(AccountDto accountDto) {
        return accountRepository.save(Account.of(accountDto, passwordEncoder));
    }
}