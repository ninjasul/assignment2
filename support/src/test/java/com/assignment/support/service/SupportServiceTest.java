package com.assignment.support.service;

import com.assignment.support.base.BaseRepositoryTest;
import com.assignment.support.dto.RegionDto;
import com.assignment.support.dto.SupportDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupportServiceTest extends BaseRepositoryTest {
    @Autowired
    private SupportService supportService;

    @Test
    public void findAll() {
        List<SupportDto> foundDtos = supportService.findAll();
        List<SupportDto> dtos = getAllSupportDtos();

        assertThat(foundDtos).isNotNull();
        assertThat(foundDtos.size()).isEqualTo(dtos.size());

        for (int i = 0; i < foundDtos.size(); ++i ) {
            assertThat(foundDtos.get(i).getRegion()).isEqualTo(dtos.get(i).getRegion());
            assertThat(foundDtos.get(i).getTarget()).isEqualTo(dtos.get(i).getTarget());
            assertThat(foundDtos.get(i).getUsage()).isEqualTo(dtos.get(i).getUsage());
            assertThat(foundDtos.get(i).getLimits()).isEqualTo(dtos.get(i).getLimits());
            assertThat(foundDtos.get(i).getRate()).isEqualTo(dtos.get(i).getRate());
            assertThat(foundDtos.get(i).getInstitute()).isEqualTo(dtos.get(i).getInstitute());
            assertThat(foundDtos.get(i).getMgmt()).isEqualTo(dtos.get(i).getMgmt());
            assertThat(foundDtos.get(i).getReception()).isEqualTo(dtos.get(i).getReception());
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByRegionName_for_wrong_region() {
        RegionDto regionDto = new RegionDto("LA");
        supportService.findByRegionName(regionDto);
    }

    @Test
    public void findByRegionName() {
        List<SupportDto> supportDtos = getAllSupportDtos();

        for (int i = 0; i < supportDtos.size(); ++i ) {
            SupportDto foundDto = supportService.findByRegionName(new RegionDto(supportDtos.get(i).getRegion()));

            assertThat(foundDto).isNotNull();
            assertThat(foundDto.getRegion()).isEqualTo(supportDtos.get(i).getRegion());
            assertThat(foundDto.getTarget()).isEqualTo(supportDtos.get(i).getTarget());
            assertThat(foundDto.getUsage()).isEqualTo(supportDtos.get(i).getUsage());
            assertThat(foundDto.getLimits()).isEqualTo(supportDtos.get(i).getLimits());
            assertThat(foundDto.getRate()).isEqualTo(supportDtos.get(i).getRate());
            assertThat(foundDto.getInstitute()).isEqualTo(supportDtos.get(i).getInstitute());
            assertThat(foundDto.getMgmt()).isEqualTo(supportDtos.get(i).getMgmt());
            assertThat(foundDto.getReception()).isEqualTo(supportDtos.get(i).getReception());
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateByRegionName_for_wrong_region() {
        SupportDto supportDto = SupportDto.builder()
                .region("뉴욕시")
                .build();

        supportService.updateByRegionName(supportDto);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateByRegionName_for_null_rate() {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .rate(null)
                .build();

        supportService.updateByRegionName(supportDto);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateByRegionName_for_empty_rate() {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .rate("")
                .build();

        supportService.updateByRegionName(supportDto);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateByRegionName_for_wrong_rate() {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .rate("잘못된 보전 비율")
                .build();

        supportService.updateByRegionName(supportDto);
    }

    @Test
    public void updateByRegionName() {
        List<SupportDto> updatedDtos = getUpdatedSupportDtos("updated");

        for (int i = 0; i < updatedDtos.size(); ++i ) {
            SupportDto updatedDto = supportService.updateByRegionName(updatedDtos.get(i));

            assertThat(updatedDto).isNotNull();
            assertThat(updatedDto.getRegion()).isEqualTo(updatedDtos.get(i).getRegion());
            assertThat(updatedDto.getTarget()).isEqualTo(updatedDtos.get(i).getTarget());
            assertThat(updatedDto.getUsage()).isEqualTo(updatedDtos.get(i).getUsage());
            assertThat(updatedDto.getLimits()).isEqualTo(updatedDtos.get(i).getLimits());
            assertThat(updatedDto.getRate()).isEqualTo(updatedDtos.get(i).getRate());
            assertThat(updatedDto.getInstitute()).isEqualTo(updatedDtos.get(i).getInstitute());
            assertThat(updatedDto.getMgmt()).isEqualTo(updatedDtos.get(i).getMgmt());
            assertThat(updatedDto.getReception()).isEqualTo(updatedDtos.get(i).getReception());
        }
    }
}