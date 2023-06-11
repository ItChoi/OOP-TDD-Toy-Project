package com.example.itemorderapplication.common.exception;

import com.example.itemorderapplication.common.enumeration.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SoldOutExceptionTest {

    @DisplayName("솔드아웃_익셉션_테스트")
    @Test
    void 솔드아웃_익셉션_테스트() {
        String itemName = "WAVE SWEATER - IVORY";
        SoldOutException soldOutException = new SoldOutException(itemName);
        assertThat(soldOutException.getErrorResult().getMessage())
                .isEqualTo(ErrorType.formatErrorMsg(ErrorType.INSUFFICIENT_STOCK_QUANTITY, itemName));
    }

}