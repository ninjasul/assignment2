package com.assignment.support.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BaseResponseDto {
    public static final String SUCCESS = "성공";
    public static final String FAIL = "실패";
    public static final String NOT_FOUND = "데이터가 존재하지 않습니다.";

    private String result;
}
