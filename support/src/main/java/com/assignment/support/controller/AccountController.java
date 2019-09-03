package com.assignment.support.controller;

import com.assignment.support.dto.AccountDto;
import com.assignment.support.dto.BaseResponseDto;
import com.assignment.support.entity.Account;
import com.assignment.support.repository.AccountRepository;
import com.assignment.support.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

import static com.assignment.support.dto.BaseResponseDto.ACCOUNT_CREATION;
import static com.assignment.support.dto.BaseResponseDto.SUCCESS;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/signup")
    public BaseResponseDto signup(@Valid @RequestBody AccountDto accountDto) {
        Account newAccount = accountService.createNew(accountDto);
        return new BaseResponseDto(newAccount.getUsername() + " " + ACCOUNT_CREATION + " " + SUCCESS);
    }

    @PostMapping("/signin")
    public BaseResponseDto signin(@Valid @RequestParam AccountDto accountDto) {
        accountService.createNew(accountDto);
        return new BaseResponseDto(ACCOUNT_CREATION + " " + SUCCESS);
    }

}