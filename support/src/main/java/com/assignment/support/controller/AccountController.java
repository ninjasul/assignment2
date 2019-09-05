package com.assignment.support.controller;

import com.assignment.support.dto.AccountDto;
import com.assignment.support.entity.Account;
import com.assignment.support.security.JwtTokenProvider;
import com.assignment.support.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody AccountDto accountDto) {
        Account newAccount = accountService.createNew(accountDto);
        return jwtTokenProvider.createToken(newAccount.getUsername(), Collections.singletonList(newAccount.getRole()));
    }

    @PostMapping("/signin")
    public String signin(@Valid @RequestBody AccountDto accountDto) {
        Account account = accountService.findByUsernameAndPassword(accountDto);
        return jwtTokenProvider.createToken(account.getUsername(), Collections.singletonList(account.getRole()));
    }
}