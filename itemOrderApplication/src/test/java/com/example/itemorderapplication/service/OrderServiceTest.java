package com.example.itemorderapplication.service;

import com.example.itemorderapplication.common.Const;
import com.example.itemorderapplication.common.exception.SoldOutException;
import com.example.itemorderapplication.domain.Item;
import com.example.itemorderapplication.domain.Order;
import com.example.itemorderapplication.domain.OrderItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

class OrderServiceTest {

    OrderService orderService = new OrderService();
    ItemService itemService = new ItemService();
    static Item firstItem;
    static Item secondsItem;

    @BeforeAll
    public static void beforeAll() {
        firstItem = ItemServiceTest.getFirstItemOrThrowException();
        secondsItem = ItemServiceTest.getSecondItemOrThrowException();

    }

    @DisplayName("주문 - 멀티 스레드 테스트")
    @RepeatedTest(10)
    void 주문_멀티_스레드_테스트() {
        Long targetItemId = 1L;
        Item item = itemService.findByIdOrThrow(targetItemId);
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < item.getStockQuantity() + 5; i++) {
            executorService.submit(() -> {
                Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
                orderItemsWithItemId.put(targetItemId, new OrderItem(targetItemId, 1));
                orderService.order(new Order(orderItemsWithItemId));
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertThat(item.getStockQuantity()).isGreaterThanOrEqualTo(0);
    }

    @DisplayName("SoldOutException 예외 테스트")
    @Test
    void 주문_솔드아웃_익셉션() {
        assertThatThrownBy(() -> {
            Optional.empty().orElseThrow(() -> new NoSuchElementException());
        }).isInstanceOf(NoSuchElementException.class);

        assertThatThrownBy(() -> {
            Order order = new Order(
                    Map.of(firstItem.getId(), new OrderItem(firstItem.getId(), firstItem.getStockQuantity() + 5))
            );
            orderService.order(order);
        }).isInstanceOf(SoldOutException.class);
    }

    @DisplayName("주문 완료 수량 테스트")
    @Test
    void 주문_완료_수량_테스트() {
        int stockQuantity = firstItem.getStockQuantity();
        int orderItemQuantity = 3;

        Map<Long, OrderItem> tempOrderItemsWithItemId = new HashMap<>();
        tempOrderItemsWithItemId.put(firstItem.getId(), new OrderItem(firstItem.getId(), orderItemQuantity));
        orderService.order(new Order(tempOrderItemsWithItemId));

        assertThat(stockQuantity).isEqualTo(firstItem.getStockQuantity() + orderItemQuantity);
    }

    @DisplayName("주문 실패시 저장소에 아이템 복구 테스트")
    @Test
    void 주문_실패시_아이템_저장소_복구() {
        // 구매 전 첫 번째 아이템 저장 수량
        int stockQuantity = firstItem.getStockQuantity();

        assertThatCode(() -> {
            Long firstItemId = firstItem.getId();
            Long secondItemId = secondsItem.getId();

            Map<Long, OrderItem> tempOrderItemsWithItemId = new LinkedHashMap<>();
            // 첫 번째 아이템 저장 수량 - 1
            tempOrderItemsWithItemId.put(firstItemId, new OrderItem(firstItemId, 1));
            tempOrderItemsWithItemId.put(secondItemId, new OrderItem(secondItemId, 555));
            assertThatCode(() -> {
                orderService.order(new Order(tempOrderItemsWithItemId));
            }).isInstanceOf(SoldOutException.class);
        }).doesNotThrowAnyException();

        assertThat(stockQuantity).isEqualTo(firstItem.getStockQuantity());
    }


    @DisplayName("이용 불가 주문 테스트")
    @Test
    void 이용_불가_주문_테스트() {
        assertThat(orderService.isAvailableOrder(null)).isFalse();
        assertThat(orderService.isAvailableOrder(new Order(null))).isFalse();
        assertThat(orderService.isAvailableOrder(new Order(new HashMap<>()))).isFalse();
    }

    @DisplayName("이용 가능 주문 테스트")
    @Test
    void 이용_가능_주문_테스트() {
        Order order = new Order(
                Map.of(1L, new OrderItem(1L, 5))
        );

        assertThat(orderService.isAvailableOrder(order)).isTrue();
    }

    @DisplayName("주문 전 주문아이템 가지고 있는지 성공 체크")
    @Test
    void 주문_전_주문_아이템_성공_체크() {
        Map<Long, OrderItem> orderItemsWithItemId = new HashMap<>();
        orderItemsWithItemId.put(firstItem.getId(), new OrderItem(firstItem.getId(), 5));
        assertThat(orderService.hasOrderItem(orderItemsWithItemId)).isTrue();
    }

    @DisplayName("주문 전 주문아이템 가지고 있는지 실패 체크")
    @Test
    void 주문_전_주문_아이템_실패_체크() {
        assertThat(orderService.hasOrderItem(new HashMap<>())).isFalse();
        assertThat(orderService.hasOrderItem(null)).isFalse();
    }

    @DisplayName("주문 종료 메시지 테스트")
    @Test
    void 주문_종료_메시지_테스트() {
        assertThat(orderService.quitOrder()).isNotBlank();
        assertThat(orderService.quitOrder()).isEqualTo(Const.USER_ORDER_COMPLETE_THANK_MENT);
    }

    @DisplayName("주문 장바구니 - 주문 null 체크")
    @Test
    void 주문한_아이템_확인_order_is_null() {
        assertThat(orderService.getItemIdInMyOrderWithInput(null)).isNull();
    }

}