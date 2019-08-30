package com.assignment.support.entity;

import com.assignment.support.dto.SupportDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Table(indexes={
        @Index(name="IDX_SUPPORT1", columnList="limit_amount desc,avg_rate"),
        @Index(name="IDX_SUPPORT2", columnList="max_rate")
})
@Entity
public class Support {

    private static final String RATE_SPLITTER = "~";
    private static final String PERCENTAGE = "%";

    @Id @GeneratedValue
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_support_to_region"), unique = true)
    private Region region;

    private String target;

    private String usage;

    @Column(nullable=false)
    private String limits;

    @Column(name="limit_amount")
    private Long limitAmount;

    @Column(nullable=false)
    private String rate;

    @Column(name="min_rate")
    private Float minRate;

    @Column(name="max_rate")
    private Float maxRate;

    @Column(name="avg_rate")
    private Float avgRate;

    private String institute;
    private String mgmt;
    private String reception;

    @Column(name="created_time")
    private LocalDateTime createdTime;

    @Column(name="modified_time")
    private LocalDateTime modifiedTime;

    public Support(Long id, Region region, String target, String usage, String limits, String rate, String institute, String mgmt, String reception, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.region = region;
        this.target = target;
        this.usage = usage;
        this.limits = limits;
        setLimitAmount(limits);
        this.rate = rate;
        setRates(rate);
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public Support(Region region, String target, String usage, String limits, String rate, String institute, String mgmt, String reception, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.region = region;
        this.target = target;
        this.usage = usage;
        this.limits = limits;
        setLimitAmount(limits);
        this.rate = rate;
        setRates(rate);
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public static Support of(SupportDto dto) {
        return new Support().builder()
                .region(new Region(null, dto.getRegion()))
                .target(dto.getTarget())
                .usage(dto.getUsage())
                .limits(dto.getLimits())
                .rate(dto.getRate())
                .institute(dto.getInstitute())
                .mgmt(dto.getMgmt())
                .reception(dto.getReception())
                .build();
    }

    public void update(SupportDto dto) {
        this.target = dto.getTarget();
        this.usage = dto.getUsage();
        this.limits = dto.getLimits();
        setLimitAmount(limits);
        this.rate = dto.getRate();
        setRates(rate);
        this.institute = dto.getInstitute();
        this.mgmt = dto.getMgmt();
        this.reception = dto.getReception();
        this.modifiedTime = LocalDateTime.now();
    }

    public void setLimitAmount(String limits) {
        this.limitAmount = Amount.calculateAmount(limits);
    }

    void setRates(String rate) {
        if (rate == null) {
            minRate = Float.MAX_VALUE;
            maxRate = Float.MAX_VALUE;
            avgRate = Float.MAX_VALUE;
            return;
        }

        if (rate.equals("대출이자 전액")) {
            minRate = 100.0f;
            maxRate = 100.0f;
            avgRate = 100.0f;
            return;
        }

        setRates(parseRates(rate));
    }

    private List<Float> parseRates(String rate) {
        try {
            return Arrays.stream(rate.split(RATE_SPLITTER))
                    .map(r -> r.replaceAll(PERCENTAGE, ""))
                    .map(Float::parseFloat)
                    .filter(r -> r <= 100.0f)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setRates(List<Float> rates) {
        if (rates == null || rates.size() <= 0) {
            minRate = Float.MAX_VALUE;
            maxRate = Float.MAX_VALUE;
            avgRate = Float.MAX_VALUE;
        }
        else if (rates.size() == 1) {
            minRate = rates.get(0);
            maxRate = rates.get(0);
            avgRate = (minRate + maxRate)/2;
        }
        else {
            minRate = (Float.compare(rates.get(0), rates.get(1)) >= 0) ? rates.get(1) : rates.get(0);
            maxRate = (Float.compare(rates.get(0), rates.get(1)) < 0) ? rates.get(1) : rates.get(0);
            avgRate = (minRate + maxRate)/2;
        }
    }

    public static SupportBuilder builder() {
        return new SupportBuilder();
    }

    public static class SupportBuilder {
        private Long id;
        private Region region;
        private String target;
        private String usage;
        private String limits;
        private String rate;
        private String institute;
        private String mgmt;
        private String reception;
        private LocalDateTime createdTime;
        private LocalDateTime modifiedTime;

        SupportBuilder() {
        }

        public Support.SupportBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Support.SupportBuilder region(Region region) {
            this.region = region;
            return this;
        }

        public Support.SupportBuilder target(String target) {
            this.target = target;
            return this;
        }

        public Support.SupportBuilder usage(String usage) {
            this.usage = usage;
            return this;
        }

        public Support.SupportBuilder limits(String limits) {
            this.limits = limits;
            return this;
        }

        public Support.SupportBuilder rate(String rate) {
            this.rate = rate;
            return this;
        }

        public Support.SupportBuilder institute(String institute) {
            this.institute = institute;
            return this;
        }

        public Support.SupportBuilder mgmt(String mgmt) {
            this.mgmt = mgmt;
            return this;
        }

        public Support.SupportBuilder reception(String reception) {
            this.reception = reception;
            return this;
        }

        public Support.SupportBuilder createdTime(LocalDateTime createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public Support.SupportBuilder modifiedTime(LocalDateTime modifiedTime) {
            this.modifiedTime = modifiedTime;
            return this;
        }

        public Support build() {
            return new Support(id, region, target, usage, limits, rate, institute, mgmt, reception, createdTime, modifiedTime);
        }

        public String toString() {
            return "Support.SupportBuilder(id=" + this.id + ", region=" + this.region + ", target=" + this.target + ", usage=" + this.usage + ", limits=" + this.limits + ", rate=" + this.rate + ", institute=" + this.institute + ", mgmt=" + this.mgmt + ", reception=" + this.reception + ", createdTime=" + this.createdTime + ", modifiedTime=" + this.modifiedTime + ")";
        }
    }
}