package com.assignment.support.service;

import com.assignment.support.dto.RegionDto;
import com.assignment.support.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<RegionDto> findBestRegions(int count) {
        return Optional.ofNullable(regionRepository.findBestRegions(count))
                        .orElseThrow(EntityNotFoundException::new)
                        .stream()
                        .map(RegionDto::of)
                        .collect(Collectors.toList());
    }

    public RegionDto findSmallestMaxRateRegion() {
        return Optional.ofNullable(regionRepository.findSmallestMaxRateRegion())
                        .map(RegionDto::of)
                        .orElseThrow(EntityNotFoundException::new);
    }
}