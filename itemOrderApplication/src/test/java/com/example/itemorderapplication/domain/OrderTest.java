package com.example.itemorderapplication.domain;

import com.example.itemorderapplication.service.ItemServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    Order order;
    Item item = ItemServiceTest.getFirstItemOrThrowException();

    final int orderItemQuantity = 3;


    @BeforeEach
    void beforeEach() {
        Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
        orderItemsWithItemId.put(item.getId(), new OrderItem(item.getId(), orderItemQuantity));
        order = new Order(orderItemsWithItemId);
    }

    @DisplayName("주문 아이템 수량 추가 테스트")
    @Test
    void 주문_아이템_수량_추가_테스트() {
        int addOrderItemQuantity = 2;
        order.addOrderItemQuantity(item.getId(), addOrderItemQuantity);
        OrderItem orderItem = order.getOrderItemsWithItemId().get(item.getId());
        assertThat(orderItem.getQuantity()).isEqualTo(orderItemQuantity + addOrderItemQuantity);

        order.addOrderItemQuantity(item.getId(), -12);
        assertThat(orderItem.getQuantity()).isEqualTo(orderItemQuantity + addOrderItemQuantity);
    }


    @DisplayName("주문 아이템 수량 빼기 테스트")
    @Test
    void 주문_아이템_수량_빼기_테스트() {
        int minusOrderItemQuantity = 2;
        order.minusOrderItemQuantity(item.getId(), minusOrderItemQuantity);
        OrderItem orderItem = order.getOrderItemsWithItemId().get(item.getId());
        assertThat(orderItem.getQuantity()).isEqualTo(orderItemQuantity - minusOrderItemQuantity);

        order.minusOrderItemQuantity(item.getId(), -12);
        assertThat(orderItem.getQuantity()).isEqualTo(orderItemQuantity - minusOrderItemQuantity);
    }

    @DisplayName("주문 아이템 수량 0개 된 경우 OrderItem 삭제 테스트")
    @Test
    void 주문_아이템_수량_0개_삭제_테스트() {
        int minusOrderItemQuantity = 2;
        OrderItem orderItem = order.getOrderItemsWithItemId().get(item.getId());
        assertThat(orderItem.getItemId()).isEqualTo(item.getId());

        order.minusOrderItemQuantity(item.getId(), orderItemQuantity);
        orderItem = order.getOrderItemsWithItemId().get(item.getId());

        assertThat(orderItem).isNull();
    }
}