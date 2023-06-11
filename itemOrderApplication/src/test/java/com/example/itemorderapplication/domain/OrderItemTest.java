package com.example.itemorderapplication.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @DisplayName("주문 아이템 수량 추가 테스트")
    @Test
    void 주문_아이템_수량_추가_테스트() {
        int quantity = 5;
        int addQuantity = 3;

        OrderItem orderItem = new OrderItem(1L, quantity);
        orderItem.addQuantity(addQuantity);
        assertThat(orderItem.getQuantity()).isEqualTo(quantity + addQuantity);
    }

    @DisplayName("주문 아이템 수량 빼기 테스트")
    @Test
    void 주문_아이템_수량_빼기_테스트() {
        int quantity = 5;
        int minusQuantity = 3;

        OrderItem orderItem = new OrderItem(1L, quantity);
        orderItem.minusQuantity(minusQuantity);
        assertThat(orderItem.getQuantity()).isEqualTo(quantity - minusQuantity);
    }
}