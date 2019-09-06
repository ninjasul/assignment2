package com.assignment.support.controller;

import com.assignment.support.dto.BaseResponseDto;
import com.assignment.support.exception.UnAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

import java.security.InvalidParameterException;

import static com.assignment.support.dto.BaseResponseDto.*;

@RestControllerAdvice
@Slf4j
public class BaseControllerAdvice {

    @ExceptionHandler({EntityNotFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponseDto notFound(Exception e) {
        e.printStackTrace();
        return new BaseResponseDto(NOT_FOUND);
    }

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            InvalidDataAccessApiUsageException.class,
            NumberFormatException.class,
            MethodArgumentNotValidException.class,
            HttpRequestMethodNotSupportedException.class,
    })

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponseDto badRequest(Exception e) {
        e.printStackTrace();
        return new BaseResponseDto(FAIL);
    }

    @ExceptionHandler({InvalidParameterException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponseDto invalidParameter(Exception e) {
        e.printStackTrace();
        return new BaseResponseDto(e.getMessage());
    }

    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public BaseResponseDto unAuthentication(Exception e) {
        e.printStackTrace();
        return new BaseResponseDto(UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponseDto exceptionOccurred(Exception e) {
        e.printStackTrace();
        return new BaseResponseDto(FAIL);
    }
}