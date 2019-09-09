package com.assignment.support.controller;

import com.assignment.support.base.BaseControllerTest;
import com.assignment.support.base.WithAdmin;
import com.assignment.support.base.WithUser;
import com.assignment.support.dto.RegionDto;
import com.assignment.support.dto.SupportDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.assignment.support.dto.BaseResponseDto.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SupportControllerTest extends BaseControllerTest {

    @Test
    @WithAnonymousUser
    public void querySupports_by_anonymous() throws Exception {
        mockMvc.perform(get("/api/support"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void querySupports_by_user() throws Exception {
        List<SupportDto> dtos = getAllSupportDtos();

        ResultActions result = mockMvc.perform(get("/api/support"))
                .andDo(print())
                .andExpect(status().isOk());

        for (int i = 0; i < dtos.size(); ++i ) {
            result.andExpect(jsonPath(String.format("$[%d].region", i), is(dtos.get(i).getRegion())))
                    .andExpect(jsonPath(String.format("$[%d].target", i), is(dtos.get(i).getTarget())))
                    .andExpect(jsonPath(String.format("$[%d].usage", i), is(dtos.get(i).getUsage())))
                    .andExpect(jsonPath(String.format("$[%d].limits", i), is(dtos.get(i).getLimits())))
                    .andExpect(jsonPath(String.format("$[%d].rate", i), is(dtos.get(i).getRate())))
                    .andExpect(jsonPath(String.format("$[%d].institute", i), is(dtos.get(i).getInstitute())))
                    .andExpect(jsonPath(String.format("$[%d].mgmt", i), is(dtos.get(i).getMgmt())))
                    .andExpect(jsonPath(String.format("$[%d].reception", i), is(dtos.get(i).getReception())));
        }
    }

    @Test
    @WithAdmin
    public void querySupports_by_admin() throws Exception {
        List<SupportDto> dtos = getAllSupportDtos();

        ResultActions result = mockMvc.perform(get("/api/support"))
                .andDo(print())
                .andExpect(status().isOk());

        for (int i = 0; i < dtos.size(); ++i ) {
            result.andExpect(jsonPath(String.format("$[%d].region", i), is(dtos.get(i).getRegion())))
                    .andExpect(jsonPath(String.format("$[%d].target", i), is(dtos.get(i).getTarget())))
                    .andExpect(jsonPath(String.format("$[%d].usage", i), is(dtos.get(i).getUsage())))
                    .andExpect(jsonPath(String.format("$[%d].limits", i), is(dtos.get(i).getLimits())))
                    .andExpect(jsonPath(String.format("$[%d].rate", i), is(dtos.get(i).getRate())))
                    .andExpect(jsonPath(String.format("$[%d].institute", i), is(dtos.get(i).getInstitute())))
                    .andExpect(jsonPath(String.format("$[%d].mgmt", i), is(dtos.get(i).getMgmt())))
                    .andExpect(jsonPath(String.format("$[%d].reception", i), is(dtos.get(i).getReception())));
        }
    }

    @Test
    @WithAnonymousUser
    public void querySupport_for_null_region_by_anonymous() throws Exception {
        RegionDto regionDto = new RegionDto(null);

        mockMvc.perform(post("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(regionDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void querySupport_for_null_region_by_user() throws Exception {
        RegionDto regionDto = new RegionDto(null);

        mockMvc.perform(post("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(regionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAdmin
    public void querySupport_for_null_region_by_admin() throws Exception {
        RegionDto regionDto = new RegionDto(null);

        mockMvc.perform(post("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(regionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAnonymousUser
    public void querySupport_for_empty_region_by_anonymous() throws Exception {
        RegionDto regionDto = new RegionDto("");

        mockMvc.perform(post("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(regionDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void querySupport_for_empty_region_by_user() throws Exception {
        RegionDto regionDto = new RegionDto("");

        mockMvc.perform(post("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(regionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(NOT_FOUND)));
    }

    @Test
    @WithAdmin
    public void querySupport_for_empty_region_by_admin() throws Exception {
        RegionDto regionDto = new RegionDto("");

        mockMvc.perform(post("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(regionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(NOT_FOUND)));
    }

    @Test
    @WithAnonymousUser
    public void querySupport_for_wrong_region_by_anonymous() throws Exception {
        RegionDto regionDto = new RegionDto("뉴욕시");

        mockMvc.perform(post("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(regionDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void querySupport_for_wrong_region_by_user() throws Exception {
        RegionDto regionDto = new RegionDto("뉴욕시");

        mockMvc.perform(post("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(regionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(NOT_FOUND)));
    }

    @Test
    @WithAdmin
    public void querySupport_for_wrong_region_by_admin() throws Exception {
        RegionDto regionDto = new RegionDto("뉴욕시");

        mockMvc.perform(post("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(regionDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(NOT_FOUND)));
    }

    @Test
    @WithAnonymousUser
    public void querySupport_by_anonymous() throws Exception {
        List<SupportDto> supportDtos = getAllSupportDtos();

        for (int i = 0; i < supportDtos.size(); ++i ) {
            mockMvc.perform(post("/api/support")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(new RegionDto(supportDtos.get(i).getRegion()))))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @WithUser
    public void querySupport_by_user() throws Exception {
        List<SupportDto> supportDtos = getAllSupportDtos();

        for (int i = 0; i < supportDtos.size(); ++i ) {
            mockMvc.perform(post("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(new RegionDto(supportDtos.get(i).getRegion()))))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.region", is(supportDtos.get(i).getRegion())))
                    .andExpect(jsonPath("$.target", is(supportDtos.get(i).getTarget())))
                    .andExpect(jsonPath("$.usage", is(supportDtos.get(i).getUsage())))
                    .andExpect(jsonPath("$.limits", is(supportDtos.get(i).getLimits())))
                    .andExpect(jsonPath("$.rate", is(supportDtos.get(i).getRate())))
                    .andExpect(jsonPath("$.institute", is(supportDtos.get(i).getInstitute())))
                    .andExpect(jsonPath("$.mgmt", is(supportDtos.get(i).getMgmt())))
                    .andExpect(jsonPath("$.reception", is(supportDtos.get(i).getReception())));
        }
    }

    @Test
    @WithAdmin
    public void querySupport_by_admin() throws Exception {
        List<SupportDto> supportDtos = getAllSupportDtos();

        for (int i = 0; i < supportDtos.size(); ++i ) {
            mockMvc.perform(post("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(new RegionDto(supportDtos.get(i).getRegion()))))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.region", is(supportDtos.get(i).getRegion())))
                    .andExpect(jsonPath("$.target", is(supportDtos.get(i).getTarget())))
                    .andExpect(jsonPath("$.usage", is(supportDtos.get(i).getUsage())))
                    .andExpect(jsonPath("$.limits", is(supportDtos.get(i).getLimits())))
                    .andExpect(jsonPath("$.rate", is(supportDtos.get(i).getRate())))
                    .andExpect(jsonPath("$.institute", is(supportDtos.get(i).getInstitute())))
                    .andExpect(jsonPath("$.mgmt", is(supportDtos.get(i).getMgmt())))
                    .andExpect(jsonPath("$.reception", is(supportDtos.get(i).getReception())));
        }
    }

    @Test
    @WithAnonymousUser
    public void updateSupport_for_null_region_by_anonymous() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(null)
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void updateSupport_for_null_region_by_user() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(null)
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAdmin
    public void updateSupport_for_null_region_by_admin() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(null)
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAnonymousUser
    public void updateSupport_for_empty_region_by_anonymous() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region("")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void updateSupport_for_empty_region_by_user() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region("")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAdmin
    public void updateSupport_for_empty_region_by_admin() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region("")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAnonymousUser
    public void updateSupport_for_wrong_region_by_anonymous() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region("뉴욕시")
                .build();

        mockMvc.perform(put("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void updateSupport_for_wrong_region_by_user() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region("뉴욕시")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(NOT_FOUND)));
    }

    @Test
    @WithAdmin
    public void updateSupport_for_wrong_region_by_admin() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region("뉴욕시")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(NOT_FOUND)));
    }

    @Test
    @WithAnonymousUser
    public void updateSupport_for_null_limits_by_anonymous() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits(null)
                .rate("10%~20%")
                .build();

        mockMvc.perform(put("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void updateSupport_for_null_limits_by_user() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits(null)
                .rate("10%~20%")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAdmin
    public void updateSupport_for_null_limits_by_admin() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits(null)
                .rate("10%~20%")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAnonymousUser
    public void updateSupport_for_empty_limits_by_anonymous() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("")
                .rate("10%~20%")
                .build();

        mockMvc.perform(put("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void updateSupport_for_empty_limits_by_user() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("")
                .rate("10%~20%")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(SUCCESS)));
    }

    @Test
    @WithAdmin
    public void updateSupport_for_empty_limits_by_admin() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("")
                .rate("10%~20%")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(SUCCESS)));
    }

    @Test
    @WithAnonymousUser
    public void updateSupport_for_null_rate_by_anonymous() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("10억원 이내")
                .rate(null)
                .build();

        mockMvc.perform(put("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void updateSupport_for_null_rate_by_user() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("10억원 이내")
                .rate(null)
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAdmin
    public void updateSupport_for_null_rate_by_admin() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("10억원 이내")
                .rate(null)
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result", is(FAIL)));
    }

    @Test
    @WithAnonymousUser
    public void updateSupport_for_empty_rate_by_anonymous() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("30백만원 이내")
                .rate("")
                .build();

        mockMvc.perform(put("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void updateSupport_for_empty_rate_by_user() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("30백만원 이내")
                .rate("")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(SUCCESS)));
    }

    @Test
    @WithAdmin
    public void updateSupport_for_empty_rate_by_admin() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("30백만원 이내")
                .rate("")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(SUCCESS)));
    }

    @Test
    @WithAnonymousUser
    public void updateSupport_for_wrong_rate_by_anonymous() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("5천만원 이내")
                .rate("1000")
                .build();

        mockMvc.perform(put("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void updateSupport_for_wrong_rate_by_user() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("5천만원 이내")
                .rate("1000")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(SUCCESS)));
    }

    @Test
    @WithAdmin
    public void updateSupport_for_wrong_rate_by_admin() throws Exception {
        SupportDto supportDto = SupportDto.builder()
                .region(regions.get(0).getName())
                .limits("5천만원 이내")
                .rate("1000")
                .build();

        mockMvc.perform(put("/api/support")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(supportDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(SUCCESS)));
    }

    @Test
    @WithAnonymousUser
    public void updateSupport_by_anonymous() throws Exception {
        List<SupportDto> updatedDtos = getUpdatedSupportDtos("updated");

        for (int i = 0; i < updatedDtos.size(); ++i ) {
            mockMvc.perform(put("/api/support")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(updatedDtos.get(i))))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @WithUser
    public void updateSupport_by_user() throws Exception {
        List<SupportDto> updatedDtos = getUpdatedSupportDtos("updated");

        for (int i = 0; i < updatedDtos.size(); ++i ) {
            mockMvc.perform(put("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(updatedDtos.get(i))))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result", is(SUCCESS)));
        }
    }

    @Test
    @WithAdmin
    public void updateSupport_by_admin() throws Exception {
        List<SupportDto> updatedDtos = getUpdatedSupportDtos("updated");

        for (int i = 0; i < updatedDtos.size(); ++i ) {
            mockMvc.perform(put("/api/support")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(updatedDtos.get(i))))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result", is(SUCCESS)));
        }
    }
}