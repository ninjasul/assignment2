package com.assignment.support.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilTest {

    @Test
    public void isNullDefault_for_null() {
        String defaultString = "default";

        assertThat(StringUtil.isNullDefault(null, defaultString)).isEqualTo(defaultString);
    }

    @Test
    public void isNullDefault_for_empty() {
        String defaultString = "default";

        assertThat(StringUtil.isNullDefault("", defaultString)).isEqualTo("");
    }

    @Test
    public void isNullDefault() {
        String originalString = "original";
        String defaultString = "default";

        assertThat(StringUtil.isNullDefault(originalString, defaultString)).isEqualTo(originalString);
    }

}