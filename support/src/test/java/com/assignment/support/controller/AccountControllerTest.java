package com.assignment.support.controller;

import com.assignment.support.dto.AccountDto;
import com.assignment.support.dto.TokenDto;
import com.assignment.support.entity.Account;
import com.assignment.support.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.assignment.support.dto.BaseResponseDto.FAIL;
import static com.assignment.support.dto.BaseResponseDto.UNAUTHORIZED;
import static com.assignment.support.security.JwtTokenProvider.TOKEN_PREFIX;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        AccountDto accountDto = getAccountDto("user", "user");

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void signup_with_empty_username() throws Exception {
        AccountDto accountDto = getAccountDto("", "user");

        mockMvc.perform(post("/signup")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(accountDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    public void signup_with_empty_password() throws Exception {
        AccountDto accountDto = getAccountDto("user", "");

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    public void signin() throws Exception {
        AccountDto accountDto = getAccountDto("user", "user");

        accountRepository.save(Account.of(accountDto, passwordEncoder));

        getSignInResult(accountDto);
    }

    @Test
    public void signin_fail() throws Exception {
        AccountDto accountDto = getAccountDto("user", "user");

        AccountDto accountDto2 = getAccountDto("user", "user2");

        accountRepository.save(Account.of(accountDto, passwordEncoder));

        mockMvc.perform(post("/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(accountDto2)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.result", is(UNAUTHORIZED)));
    }

    @Test
    public void refreshToken_for_same_token() throws Exception {
        AccountDto accountDto = getAccountDto("user", "user");

        accountRepository.save(Account.of(accountDto, passwordEncoder));

        MvcResult mvcResult = getSignInResult(accountDto).andReturn();

        TokenDto tokenDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokenDto.class);

        mockMvc.perform(get("/refreshToken")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + tokenDto.getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.token").value(Matchers.is(tokenDto.getToken())));

    }

    @Test
    public void refreshToken_for_new_token() throws Exception {
        AccountDto accountDto = getAccountDto("user", "user");

        accountRepository.save(Account.of(accountDto, passwordEncoder));

        MvcResult mvcResult = getSignInResult(accountDto).andReturn();

        TokenDto tokenDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokenDto.class);

        Thread.sleep(2000);

        mockMvc.perform(get("/refreshToken")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + tokenDto.getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.token").value(Matchers.not(tokenDto.getToken())));

    }

    private AccountDto getAccountDto(String user, String password) {
        return new AccountDto().builder()
                .username(user)
                .password(password)
                .build();
    }

    private ResultActions getSignInResult(AccountDto accountDto) throws Exception {
        return mockMvc.perform(post("/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(accountDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }
}   