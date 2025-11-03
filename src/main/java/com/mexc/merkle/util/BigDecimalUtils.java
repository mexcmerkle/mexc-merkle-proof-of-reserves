package com.mexc.merkle.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

public class BigDecimalUtils {

    public static BigDecimal parseString(String value) {
        if (StringUtils.isBlank(value)) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(value);
    }

    public static BigDecimal safeSumBigDecimal(BigDecimal... values) {
        if (Objects.isNull(values) || values.length == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal b : values) {
            if (Objects.nonNull(b)) {
                result = result.add(b);
            }
        }
        return result;
    }

    public static String getBigDecimalPlainStr(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.stripTrailingZeros().toPlainString();
    }

    public static String getBigDecimalPlainStr(String amount) {
        if (StringUtils.isBlank(amount)) {
            return null;
        }
        return new BigDecimal(amount).stripTrailingZeros().toPlainString();
    }
}
