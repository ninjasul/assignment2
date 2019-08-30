package com.assignment.support.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class AmountTest {

    @Test
    public void calculateAmount_for_null() {
        assertThat(Amount.calculateAmount(null)).isEqualTo(0L);
    }

    @Test
    public void calculateAmount_for_empty() {
        String amountStr = "";

        assertThat(Amount.calculateAmount(amountStr)).isEqualTo(0L);
    }

    @Test
    public void calculateAmount_for_nonAmount1() {
        String amountStr = "8억즈음";

        assertThat(Amount.calculateAmount(amountStr)).isEqualTo(0L);
    }

    @Test
    public void calculateAmount_for_nonAmount2() {
        String amountStr = "가나다라마바사";

        assertThat(Amount.calculateAmount(amountStr)).isEqualTo(0L);
    }

    @Test
    public void calculateAmount1() {
        String amountStr = "8억원";

        assertThat(Amount.calculateAmount(amountStr)).isEqualTo(800000000L);
    }

    @Test
    public void calculateAmount2() {
        String amountStr = "300억원 이내";

        assertThat(Amount.calculateAmount(amountStr)).isEqualTo(30000000000L);
    }

    @Test
    public void calculateAmount3() {
        String amountStr = "500만원 이내";

        assertThat(Amount.calculateAmount(amountStr)).isEqualTo(5000000L);
    }

    @Test
    public void calculateAmount4() {
        String amountStr = "8000만원 이내";

        assertThat(Amount.calculateAmount(amountStr)).isEqualTo(80000000L);
    }
}