package com.assignment.support.controller;

import com.assignment.support.dto.AccountDto;
import com.assignment.support.entity.Account;
import com.assignment.support.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.assignment.support.dto.BaseResponseDto.FAIL;
import static com.assignment.support.dto.BaseResponseDto.UNAUTHORIZED;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void signup() throws Exception {
        AccountDto accountDto = new AccountDto().builder()
                .username("user")
                .password("user")
                .build();

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void signup_with_empty_username() throws Exception {
        AccountDto accountDto = new AccountDto().builder()
                                                .username("")
                                                .password("user")
                                                .build();

        mockMvc.perform(post("/signup")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(accountDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    public void signup_with_empty_password() throws Exception {
        AccountDto accountDto = new AccountDto().builder()
                .username("user")
                .password("")
                .build();

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    public void signin() throws Exception {
        AccountDto accountDto = new AccountDto().builder()
                .username("user")
                .password("user")
                .build();

        accountRepository.save(Account.of(accountDto, passwordEncoder));

        mockMvc.perform(post("/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void signin_fail() throws Exception {
        AccountDto accountDto = new AccountDto().builder()
                .username("user")
                .password("user")
                .build();

        AccountDto accountDto2 = new AccountDto().builder()
                .username("user")
                .password("user2")
                .build();

        accountRepository.save(Account.of(accountDto, passwordEncoder));

        mockMvc.perform(post("/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(accountDto2)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.result", is(UNAUTHORIZED)));
    }

}   