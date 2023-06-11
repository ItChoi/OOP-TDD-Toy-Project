package com.example.itemorderapplication.common;

public class Const {
    // 주문 입력 키
    public static final String QUIT = "q";
    public static final String ITEM_ORDER = "o";
    public static final String ORDER_COMPLETE = " ";
    public static final String CURRENT_ORDER_BASKET = "b";
    public static final String MINUS_ORDER_ITEM_QUANTITY = "d";

    // 주문 입력 멘트
    public static final String ORDER_APP_START_MENT = "입력(o[order]: 주문, q[quit]: 종료, 장바구니: b[basket], 주문 수량 뺴기: d)";
    public static final String INPUT_ITEM_ID = "상품번호";
    public static final String INPUT_ITEM_QUANTITY = "수량";

    // 주문 관련 멘트
    public static final String USER_ORDER_COMPLETE_THANK_MENT = "고객님의 주문 감사합니다.";
    public static final String NOT_EXISTS_ADDED_ITEM_MENT = "주문하실 상품이 존재하지 않습니다. 상품 번호를 다시 입력해주세요.";
    public static final String NOT_INPUT_CORRECTLY_WITH_ITEM_ID_MENT = "아이템 ID가 올바르게 입력되지 않았습니다.";

    // 아이템
    public static final String MISMATCH_ITEM_COUNT = "찾으려는 데이터 개수가 일치하지 않습니다.";

    // 배송비
    public static final int MINIMUM_PRICE_DELIVERY_FEE = 50000;
    public static final int DELIVERY_FEE = 2500;

}
