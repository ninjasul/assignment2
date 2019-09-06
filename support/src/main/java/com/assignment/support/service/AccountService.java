package com.assignment.support.service;

import com.assignment.support.dto.AccountDto;
import com.assignment.support.entity.Account;
import com.assignment.support.exception.UnAuthenticationException;
import com.assignment.support.repository.AccountRepository;
import com.assignment.support.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username)
                                .map(account -> User.builder()
                                            .username(account.getUsername())
                                            .password(passwordEncoder.encode(account.getPassword()))
                                            .roles(account.getRole().name())
                                            .build()
                                )
                                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Account login(AccountDto accountDto) throws UnAuthenticationException {
        return accountRepository.findByUsername(accountDto.getUsername())
                                .filter(account -> passwordEncoder.matches(accountDto.getPassword(), account.getPassword()))
                                .orElseThrow(UnAuthenticationException::new);
    }

    public Account createNew(AccountDto accountDto) {
        return accountRepository.save(Account.of(accountDto, passwordEncoder));
    }

    public String getRefreshedToken(String token) throws InvalidParameterException {
        if(StringUtils.isEmpty(token)) {
            throw new InvalidParameterException("토큰 값이 존재하지 않습니다.");
        }

        if (jwtTokenProvider.validateToken(token)) {
            return token;
        }

        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        log.info("auth name: {}", auth.getName());

        return jwtTokenProvider.createToken(auth.getName(), getRoles(auth));
    }

    private List<String> getRoles(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}