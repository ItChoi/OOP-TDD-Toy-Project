package com.example.itemorderapplication.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;

public class PriceUtil {

    public static boolean isOnlyDigit(String value) {
        return StringUtils.isNotBlank(value) && value.matches("^([0-9]+)$");
    }

    public static String formattedPrice(int price) {
        if (price <= 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(price);
    }

    public static String formattedWonPrice(int price) {
        if (price <= 0) {
            return "0원";
        }
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(price) + "원";
    }

}
