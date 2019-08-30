package com.assignment.support.controller;

import com.assignment.support.base.BaseControllerTest;
import com.assignment.support.dto.RegionDto;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.assignment.support.dto.BaseResponseDto.FAIL;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegionControllerTest extends BaseControllerTest {

    @Test
    public void queryBestRegions_for_wrong_request() throws Exception {
        mockMvc.perform(get("/api/region/best/0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    public void queryBestRegions() throws Exception {
        for (int size = 1; size <= regions.size(); ++size) {
            List<RegionDto> regionDtos = getSortedBestSupportRegionDtos(size);

            String uri = String.format("/api/region/best/%d", size);

            ResultActions result = mockMvc.perform(get(uri))
                    .andDo(print())
                    .andExpect(status().isOk());

            for (int j = 0; j < size; ++j) {
                String region = String.format("$[%d].region", j);
                result.andExpect(jsonPath(region, is(regionDtos.get(j).getRegion())));
            }
        }
    }

    @Test
    public void querySmallestRegion() throws Exception {
        RegionDto regionDto = getSortedSmallestMaxRateRegionDto();

        mockMvc.perform(get("/api/region/smallest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.region", is(regionDto.getRegion())));
    }
}