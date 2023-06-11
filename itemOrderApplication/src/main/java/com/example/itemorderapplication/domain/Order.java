package com.example.itemorderapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Slf4j
@Getter
public class Order {
    private Map<Long, OrderItem> orderItemsWithItemId;

    public void addOrderItemQuantity(Long itemId,
                                     int itemQuantity) {
        if (Objects.isNull(itemId) || itemQuantity <= 0) {
            return;
        }
        OrderItem orderItem = this.orderItemsWithItemId.get(itemId);
        if (Objects.isNull(orderItem)) {
            this.orderItemsWithItemId.put(itemId, new OrderItem(itemId, itemQuantity));
            return;
        }

        orderItem.addQuantity(itemQuantity);
    }

    public void minusOrderItemQuantity(Long itemId,
                                       int quantity) {
        if (Objects.isNull(itemId) || quantity <= 0) {
            return;
        }

        OrderItem orderItem = this.orderItemsWithItemId.get(itemId);
        if (Objects.isNull(orderItem)) {
            log.error("주문한 상품이 존재하지 않습니다.");
            return;
        }

        if (orderItem.getQuantity() <= quantity) {
            orderItemsWithItemId.remove(itemId);
            return;
        }

        orderItem.minusQuantity(quantity);
    }

    @Getter
    @RequiredArgsConstructor
    public enum StopOrderFieldType {
        ITEM_ID("상품 번호 입력"),
        ITEM_QUANTITY("상품 수량 입력");

        private final String action;
    }
}
