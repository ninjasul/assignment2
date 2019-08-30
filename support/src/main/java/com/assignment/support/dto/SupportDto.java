package com.assignment.support.dto;

import com.assignment.support.entity.Support;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@Builder
public class SupportDto {

    @NotEmpty
    private String region;
    private String target;
    private String usage;
    private String limits;
    private String rate;
    private String institute;
    private String mgmt;
    private String reception;

    public SupportDto(@NotEmpty String region, String target, String usage, String limits, String rate, String institute, String mgmt, String reception) {
        this.region = region;
        this.target = target;
        this.usage = usage;
        this.limits = limits;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
    }

    public static SupportDto of(Support support) {
        return new SupportDto().builder()
                                .region(support.getRegion().getName())
                                .target(support.getTarget())
                                .usage(support.getUsage())
                                .limits(support.getLimits())
                                .rate(support.getRate())
                                .institute(support.getInstitute())
                                .mgmt(support.getMgmt())
                                .reception(support.getReception())
                                .build();
    }
}