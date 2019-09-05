package com.assignment.support.config;

import com.assignment.support.security.JwtAuthenticationFilter;
import com.assignment.support.security.JwtTokenProvider;
import com.assignment.support.security.UsernamePasswordAuthFilter;
import com.assignment.support.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers( "/h2-console/**", "/signin", "/signup", "/refresh").permitAll()
                .anyRequest().hasRole("USER")
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
            //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
               // .addFilterBefore(new UsernamePasswordAuthFilter(authenticationManagerBean(), jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}