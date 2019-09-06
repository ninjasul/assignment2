package com.assignment.support.controller;

import com.assignment.support.dto.AccountDto;
import com.assignment.support.dto.TokenDto;
import com.assignment.support.entity.Account;
import com.assignment.support.exception.UnAuthenticationException;
import com.assignment.support.security.JwtTokenProvider;
import com.assignment.support.service.AccountService;
import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.assignment.support.security.JwtTokenProvider.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/signup", consumes = APPLICATION_JSON_UTF8_VALUE)
    public TokenDto signup(@Valid @RequestBody AccountDto accountDto) {
        Account account = accountService.createNew(accountDto);
        return new TokenDto(jwtTokenProvider.createToken(account.getUsername(), account.getRoles()));
    }

    @PostMapping(value = "/signin", consumes = APPLICATION_JSON_UTF8_VALUE)
    public TokenDto signin(@Valid @RequestBody AccountDto accountDto) throws UnAuthenticationException {
        Account account = accountService.login(accountDto);
        return new TokenDto(jwtTokenProvider.createToken(account.getUsername(), account.getRoles()));
    }

    @RequestMapping("/refreshToken")
    public TokenDto refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        return new TokenDto(accountService.getRefreshedToken(getToken(header)));
    }

    private String getToken(String header) {
        return Optional.ofNullable(header)
                        .map(h -> h.replace(TOKEN_PREFIX, ""))
                        .orElse(null);
    }
}