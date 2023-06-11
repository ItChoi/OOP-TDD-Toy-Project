package com.example.itemorderapplication.common.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INSUFFICIENT_STOCK_QUANTITY(HttpStatus.BAD_REQUEST, "%s 재고 수량이 부족합니다."),
    NOT_INPUT_REQUIRED_DATA(HttpStatus.BAD_REQUEST, "필수 값이 입력되지 않았습니다."),
    NOT_FOUND_REQUIRED_Object(HttpStatus.BAD_REQUEST, "필수 오브젝트가 존재하지 않았습니다."),
    NOT_FOUND_ITEM(HttpStatus.BAD_REQUEST, "아이템이 존재하지 않습니다."),
    SOLD_OUT_ITEM(HttpStatus.BAD_REQUEST, "SoldOutException 발생, 주문한 상품량이 재고량보다 큽니다.");


    private final HttpStatus httpStatus;
    private final String msg;

    public static String formatErrorMsg(ErrorType errorType,
                                        String... messages) {
        return String.format(errorType.msg, messages);
    }
}
