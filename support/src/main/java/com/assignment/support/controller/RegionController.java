package com.assignment.support.controller;

import com.assignment.support.dto.RegionDto;
import com.assignment.support.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/api/region", produces = APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class RegionController {

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/best/{count}")
    public List<RegionDto> queryBestRegions(@PathVariable int count) {
        return regionService.findBestRegions(count);
    }

    @RequestMapping(value = "/smallest")
    public RegionDto querySmallestRegion() {
        return regionService.findSmallestMaxRateRegion();
    }
}