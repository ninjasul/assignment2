package com.assignment.support.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public enum Amount {

    십(10L),
    백(100L),
    천(1_000L),
    만(10_000L),
    억(100_000_000L);

    private static Map<String, Long> amountMap = new HashMap<>();

    static {
        for (Amount curAmount : values()) {
            amountMap.put(curAmount.name(), curAmount.amount);
        }
    }

    private Long amount;

    Amount(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public static Long calculateAmount(String amountStr) {
        String AMOUNT_SPLITTER = "원";
        String AMOUNT_GROUP_REG_EXP = "(?<=\\d)(?=[가-힣])";

        try {
            return Optional.ofNullable(amountStr)
                    .filter(str -> str.contains(AMOUNT_SPLITTER))
                    .map(str -> str.split(AMOUNT_SPLITTER))
                    .map(group -> group[0])
                    .map(firstGroup -> firstGroup.split(AMOUNT_GROUP_REG_EXP))
                    .map(amountGroup -> {
                        Long sum = Long.parseLong(amountGroup[0]);
                        for (char curChar : amountGroup[1].toCharArray()) {
                            sum *= amountMap.getOrDefault(Character.toString(curChar), 1L);
                        }
                        return sum;
                    })
                    .orElse(0L);
        } catch (Exception e) {
            return 0L;
        }
    }
}
