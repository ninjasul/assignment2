package com.assignment.support.util;

import org.springframework.util.StringUtils;

public class StringUtil {
    public static String isEmptyDefault(final String str, final String defaultStr) {
        if (StringUtils.isEmpty(str)) {
            return defaultStr;
        } else {
            return str;
        }
    }

    public static String isNullDefault(final String str, final String defaultStr) {
        if (str == null) {
            return defaultStr;
        } else {
            return str;
        }
    }

}