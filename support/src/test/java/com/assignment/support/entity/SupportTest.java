package com.assignment.support.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportTest {

    @Test
    public void setRates_for_null() {
        Support support = new Support();
        support.setRates(null);

        assertThat(support.getMinRate()).isEqualTo(Float.MAX_VALUE);
        assertThat(support.getMaxRate()).isEqualTo(Float.MAX_VALUE);
        assertThat(support.getAvgRate()).isEqualTo(Float.MAX_VALUE);
    }

    @Test
    public void setRates_for_empty() {
        Support support = new Support();
        support.setRates("");

        assertThat(support.getMinRate()).isEqualTo(Float.MAX_VALUE);
        assertThat(support.getMaxRate()).isEqualTo(Float.MAX_VALUE);
        assertThat(support.getAvgRate()).isEqualTo(Float.MAX_VALUE);
    }
}
