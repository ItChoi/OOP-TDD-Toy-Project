package com.example.itemorderapplication.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceUtilTest {

    @DisplayName("only 숫자 문자열 테스트")
    @Test
    void only_숫자_문자열_테스트() {
        assertThat(PriceUtil.isOnlyDigit("123123213123")).isTrue();
        assertThat(PriceUtil.isOnlyDigit("")).isFalse();
        assertThat(PriceUtil.isOnlyDigit(" ")).isFalse();
        assertThat(PriceUtil.isOnlyDigit(null)).isFalse();
        assertThat(PriceUtil.isOnlyDigit("2aa2")).isFalse();
        assertThat(PriceUtil.isOnlyDigit("aaa")).isFalse();
        assertThat(PriceUtil.isOnlyDigit("12 34")).isFalse();
        assertThat(PriceUtil.isOnlyDigit("1234 ")).isFalse();
        assertThat(PriceUtil.isOnlyDigit(" 1234 ")).isFalse();
        assertThat(PriceUtil.isOnlyDigit(" 1234")).isFalse();
    }

    @DisplayName("int 타입 숫자 자릿수 포맷팅 문자열 가격 반환 테스트")
    @Test
    void int_타입_숫자_자릿수_포맷팅_문자열_가격_반환_테스트() {
        int price1 = 50000;
        int price2 = 5000;
        int price3 = 0;
        int price4 = -1;
        assertThat(PriceUtil.formattedPrice(price1)).isEqualTo("50,000");
        assertThat(PriceUtil.formattedPrice(price2)).isEqualTo("5,000");
        assertThat(PriceUtil.formattedPrice(price3)).isEqualTo("0");
        assertThat(PriceUtil.formattedPrice(price4)).isEqualTo("0");
    }

    @DisplayName("int 타입 숫자 자릿수 포맷팅 문자열 가격 한화 반환 테스트")
    @Test
    void int_타입_숫자_자릿수_포맷팅_문자열_가격_한화_반환_테스트() {
        int price1 = 50000;
        int price2 = 5000;
        int price3 = 0;
        int price4 = -1;
        assertThat(PriceUtil.formattedWonPrice(price1)).isEqualTo("50,000원");
        assertThat(PriceUtil.formattedWonPrice(price2)).isEqualTo("5,000원");
        assertThat(PriceUtil.formattedWonPrice(price3)).isEqualTo("0원");
        assertThat(PriceUtil.formattedWonPrice(price4)).isEqualTo("0원");
    }

}