package com.assignment.support.dto;

import com.assignment.support.entity.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Builder
public class RegionDto {

    @NotEmpty
    private String region;

    public RegionDto(String region) {
        this.region = region;
    }

    public static RegionDto of(Region region) {
        return new RegionDto().builder()
                    .region(region.getName())
                    .build();
    }
}