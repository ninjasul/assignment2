package com.assignment.support.service;

import com.assignment.support.base.BaseRepositoryTest;
import com.assignment.support.dto.RegionDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionServiceTest extends BaseRepositoryTest {

    @Autowired
    private RegionService regionService;

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findBestRegions_for_zero_region() {
        regionService.findBestRegions(0);
    }

    @Test
    public void findBestRegions() {
        for (int curSize = 1; curSize <= supports.size(); ++curSize ) {
            List<RegionDto> regionDtos = regionService.findBestRegions(curSize);
            List<RegionDto> bestRegionDtos = getSortedBestSupportRegionDtos(curSize);

            assertThat(regionDtos).isNotNull();
            assertThat(regionDtos.size()).isEqualTo(curSize);

            for (int j = 0; j < curSize; ++j) {
                assertThat(regionDtos.get(j).getRegion()).isEqualTo(bestRegionDtos.get(j).getRegion());
            }
        }
    }

    @Test
    public void findSmallestMaxRateRegions() {
        RegionDto regionDto = regionService.findSmallestMaxRateRegion();
        RegionDto smallestMaxRateRegionDto = getSortedSmallestMaxRateRegionDto();

        assertThat(regionDto).isNotNull();
        assertThat(regionDto.getRegion()).isEqualTo(smallestMaxRateRegionDto.getRegion());
    }
}