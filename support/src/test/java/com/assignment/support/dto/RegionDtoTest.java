package com.assignment.support.dto;

import com.assignment.support.entity.Region;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RegionDtoTest {

    @Test
    public void of() {
        Region region = new Region().builder()
                                    .code("rgn0001")
                                    .name("강릉시")
                                    .build();

        RegionDto regionDto = RegionDto.of(region);

        assertThat(regionDto.getRegion()).isEqualTo(region.getName());
    }
}