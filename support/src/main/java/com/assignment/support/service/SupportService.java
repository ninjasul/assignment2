package com.assignment.support.service;

import com.assignment.support.repository.SupportRepository;
import com.assignment.support.repository.SupportRepositorySupport;
import com.assignment.support.entity.Region;
import com.assignment.support.entity.Support;
import com.assignment.support.dto.SupportDto;
import com.assignment.support.dto.RegionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SupportService {

    @Autowired
    private SupportRepository supportRepository;

    @Autowired
    private SupportRepositorySupport supportRepositorySupport;

    public List<SupportDto> findAll() {
        return Optional.ofNullable(supportRepository.findAll())
                .orElseThrow(EntityNotFoundException::new)
                .stream()
                .map(SupportDto::of)
                .collect(Collectors.toList());
    }

    public SupportDto findByRegionName(RegionDto requestDto) {
        return Optional.ofNullable(supportRepositorySupport.findByRegionName(new Region(null, requestDto.getRegion())))
                        .map(SupportDto::of)
                        .orElseThrow(EntityNotFoundException::new);

    }

    @Transactional
    public SupportDto updateByRegionName(SupportDto supportDto) {
        Support updatedSupport = Support.of(supportDto);

        Support original = Optional.ofNullable(supportRepositorySupport.findByRegionName(updatedSupport.getRegion()))
                                    .orElseThrow(EntityNotFoundException::new);
        original.update(supportDto);

        return supportDto;
    }
}