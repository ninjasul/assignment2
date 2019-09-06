package com.assignment.support.controller;

import com.assignment.support.dto.BaseResponseDto;
import com.assignment.support.dto.SupportDto;
import com.assignment.support.dto.RegionDto;
import com.assignment.support.service.CsvReadService;
import com.assignment.support.service.SupportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.assignment.support.dto.BaseResponseDto.SUCCESS;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value="/api/support", produces= APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class SupportController {

    @Autowired
    private SupportService supportService;

    @Autowired
    private CsvReadService csvReadService;

    @RequestMapping("/insert")
    public BaseResponseDto insert() throws Exception {
        csvReadService.readCsvAndSaveDb();
        return new BaseResponseDto(SUCCESS);
    }

    @GetMapping
    public List<SupportDto> querySupports() {
        return supportService.findAll();
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public SupportDto querySupport(@RequestBody RegionDto requestDto) {
        return supportService.findByRegionName(requestDto);
    }

    @PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public BaseResponseDto updateSupport(@Valid @RequestBody SupportDto supportDto) {
        supportService.updateByRegionName(supportDto);
        return new BaseResponseDto(SUCCESS);
    }
}