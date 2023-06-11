package com.example.itemorderapplication.domain;

import com.example.itemorderapplication.common.util.PriceUtil;
import com.example.itemorderapplication.service.ItemServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    Item item;

    @BeforeEach
    void beforeEach() {
        item = ItemServiceTest.getFirstItemOrThrowException();
    }

    @DisplayName("아이템 stockQuantity 빼기 테스트")
    @Test
    void 아이템_stockQuantity_빼기_테스트() {
        int stockQuantity = item.getStockQuantity();
        item.minusStockQuantity(1);

        assertThat(item.getStockQuantity()).isEqualTo(stockQuantity - 1);
    }

    @DisplayName("아이템 stockQuantity 추가 테스트")
    @Test
    void 아이템_stockQuantity_추가_테스트() {
        int stockQuantity = item.getStockQuantity();
        item.plusStockQuantity(1);

        assertThat(item.getStockQuantity()).isEqualTo(stockQuantity + 1);
    }

    @DisplayName("한화 가격 조회 테스트")
    @Test
    void 한화_가격_조회_테스트() {
        String formattedPrice = item.getFormattingWonPrice();
        assertThat(formattedPrice).isEqualTo(PriceUtil.formattedWonPrice(item.getPrice()));
    }

    @DisplayName("멀티 스레드 테스트")
    @Test
    public void 멀티_스레드_테스트() throws InterruptedException {
        int stockQuantity = item.getStockQuantity();
        ExecutorService service = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(stockQuantity);
        for (int i = 0; i < stockQuantity; i++) {
            service.execute(() -> {
                item.minusStockQuantity(1);
                latch.countDown();
            });
        }
        latch.await();
        assertThat(0).isEqualTo(item.getStockQuantity());
    }

}