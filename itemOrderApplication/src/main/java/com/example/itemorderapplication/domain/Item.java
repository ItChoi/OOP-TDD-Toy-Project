package com.example.itemorderapplication.domain;

import com.example.itemorderapplication.common.util.PriceUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public class Item {
    private Long id; // 상품 고유 번호
    private String name; // 상품명
    private int price; // 판매 가격
    private int stockQuantity; // 재고 수량

    public void minusStockQuantity(int quantity) {
        synchronized (this) {
            this.stockQuantity -= quantity;
        }
    }

    public void plusStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    public String getFormattingWonPrice() {
        return PriceUtil.formattedWonPrice(this.price);
    }
}
