package com.assignment.support.dto;

import com.assignment.support.entity.Region;
import com.assignment.support.entity.Support;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportDtoTest {

    @Test
    public void of() {
        Support support = new Support().builder()
                                        .region(new Region(null, "울산광역시"))
                                        .target("target")
                                        .usage("usage")
                                        .limits("limits")
                                        .rate("10%")
                                        .institute("institute")
                                        .mgmt("mgmt")
                                        .reception("reception")
                                        .build();

        SupportDto supportDto = SupportDto.of(support);

        assertThat(supportDto.getRegion()).isEqualTo(support.getRegion().getName());
        assertThat(supportDto.getTarget()).isEqualTo(support.getTarget());
        assertThat(supportDto.getUsage()).isEqualTo(support.getUsage());
        assertThat(supportDto.getLimits()).isEqualTo(support.getLimits());
        assertThat(supportDto.getRate()).isEqualTo(support.getRate());
        assertThat(supportDto.getInstitute()).isEqualTo(support.getInstitute());
        assertThat(supportDto.getMgmt()).isEqualTo(support.getMgmt());
        assertThat(supportDto.getReception()).isEqualTo(support.getReception());
    }
}