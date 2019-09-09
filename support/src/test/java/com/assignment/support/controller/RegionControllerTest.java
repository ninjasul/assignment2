package com.assignment.support.controller;

import com.assignment.support.base.BaseControllerTest;
import com.assignment.support.base.WithAdmin;
import com.assignment.support.base.WithUser;
import com.assignment.support.dto.RegionDto;
import com.assignment.support.entity.AccountRole;
import org.junit.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.assignment.support.dto.BaseResponseDto.FAIL;
import static com.assignment.support.entity.AccountRole.USER;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegionControllerTest extends BaseControllerTest {

    @Test
    @WithAnonymousUser
    public void queryBestRegions_for_wrong_request_by_anonymous() throws Exception {
        mockMvc.perform(get("/api/region/best/0"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void queryBestRegions_for_wrong_request_by_user() throws Exception {
        mockMvc.perform(get("/api/region/best/0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAdmin
    public void queryBestRegions_for_wrong_request_by_admin() throws Exception {
        mockMvc.perform(get("/api/region/best/0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAnonymousUser
    public void queryBestRegions_by_anonymous() throws Exception {
        String uri = String.format("/api/region/best/%d", regions.size());

        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void queryBestRegions_by_user() throws Exception {
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
    @WithAdmin
    public void queryBestRegions_by_admin() throws Exception {
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
    @WithAnonymousUser
    public void querySmallestRegion_by_anonymous() throws Exception {
        mockMvc.perform(get("/api/region/smallest").with(anonymous()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void querySmallestRegion_by_user() throws Exception {
        RegionDto regionDto = getSortedSmallestMaxRateRegionDto();

        mockMvc.perform(get("/api/region/smallest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.region", is(regionDto.getRegion())));
    }

    @Test
    @WithAdmin
    public void querySmallestRegion_by_admin() throws Exception {
        RegionDto regionDto = getSortedSmallestMaxRateRegionDto();

        mockMvc.perform(get("/api/region/smallest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.region", is(regionDto.getRegion())));
    }
}