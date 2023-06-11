package com.example.itemorderapplication.service;

import com.example.itemorderapplication.domain.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class ItemServiceTest {

    static ItemService itemService = new ItemService();



    @DisplayName("아이템 id로 아이템 찾기 성공 테스트")
    @Test
    void 성공_아이템_찾기_by_id() {
        Item firstItem = getFirstItemOrThrowException();
        Long firstItemId = firstItem.getId();
        Item findOptionalItem = itemService.findById(firstItemId)
                .orElse(null);

        Item findThrowExpItem = itemService.findByIdOrThrow(firstItemId);
        assertThatCode(() -> {
            itemService.findByIdOrThrow(firstItemId);
        }).doesNotThrowAnyException();

        assertThat(findOptionalItem).isNotNull();
        assertThat(findOptionalItem.getId()).isEqualTo(firstItemId);
        assertThat(findOptionalItem.getName()).isEqualTo(firstItem.getName());
        assertThat(findOptionalItem.getPrice()).isEqualTo(firstItem.getPrice());
        assertThat(findOptionalItem.getStockQuantity()).isEqualTo(firstItem.getStockQuantity());

        assertThat(findThrowExpItem).isNotNull();
        assertThat(findThrowExpItem.getId()).isEqualTo(firstItemId);
        assertThat(findThrowExpItem.getName()).isEqualTo(firstItem.getName());
        assertThat(findThrowExpItem.getPrice()).isEqualTo(firstItem.getPrice());
        assertThat(findThrowExpItem.getStockQuantity()).isEqualTo(firstItem.getStockQuantity());
    }

    @DisplayName("아이템 id로 아이템 찾기 실패 테스트")
    @Test
    void 실패_아이템_찾기_by_id() {
        long noItemId = 9999122L;
        Optional<Item> item = itemService.findById(noItemId);

        assertThat(item.isEmpty()).isTrue();
        assertThat(item).isEqualTo(Optional.empty());
        assertThatCode(() -> {
            item.orElseThrow(() -> new NoSuchElementException());
        }).isInstanceOf(NoSuchElementException.class);

        assertThatCode(() -> {
            itemService.findByIdOrThrow(noItemId);
        }).isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("전체 아이템 개수 테스트")
    @Test
    void 전체_아이템_개수_테스트() {
        ConcurrentLinkedQueue<Item> items = itemService.findAll();
        Map<Long, Item> itemsWithItemId = itemService.findAllWithItemId();
        assertThat(items.size()).isEqualTo(itemsWithItemId.size());
    }

    @DisplayName("올바른 아이템 id 성공 테스트")
    @Test
    void 올바른_아이템_성공_테스트() {
        Item firstItem = getFirstItemOrThrowException();
        assertThat(itemService.isRightItemId(firstItem.getId())).isTrue();
        assertThat(itemService.isRightItemId(String.valueOf(firstItem.getId()))).isTrue();
    }

    @DisplayName("올바른 아이템 id 실패 테스트")
    @Test
    void 올바른_아이템_실패_테스트() {
        Long failItemId1 = 11312312311L;
        assertThat(itemService.isRightItemId(failItemId1)).isFalse();

        Long failItemId2 = null;
        assertThat(itemService.isRightItemId(failItemId2)).isFalse();

        String failItemId3 = null;
        String failItemId4 = "";
        String failItemId5 = " ";
        assertThat(itemService.isRightItemId(failItemId3)).isFalse();
        assertThat(itemService.isRightItemId(failItemId4)).isFalse();
        assertThat(itemService.isRightItemId(failItemId5)).isFalse();
    }

    @DisplayName("구매 가능 수량 성공 테스트")
    @Test
    void 구매_가능_수량_성공_테스트() {
        Item item = getFirstItemOrThrowException();
        Long itemId = item.getId();
        int stockQuantity = item.getStockQuantity();

        assertThat(itemService.canPurchaseItemQuantity(itemId, String.valueOf(stockQuantity))).isTrue();
        assertThat(itemService.canPurchaseItemQuantity(itemId, stockQuantity)).isTrue();
    }

    @DisplayName("구매 가능 수량 실패 테스트")
    @Test
    void 구매_가능_수량_실패_테스트() {
        Item item = getFirstItemOrThrowException();
        Long itemId = item.getId();
        int stockQuantity = item.getStockQuantity() + 1;

        assertThat(itemService.canPurchaseItemQuantity(itemId, String.valueOf(stockQuantity))).isFalse();
        assertThat(itemService.canPurchaseItemQuantity(itemId, stockQuantity)).isFalse();
        assertThat(itemService.canPurchaseItemQuantity(0L, stockQuantity)).isFalse();
        assertThat(itemService.canPurchaseItemQuantity(0L, 0)).isFalse();
        assertThat(itemService.canPurchaseItemQuantity(null, stockQuantity)).isFalse();
        assertThat(itemService.canPurchaseItemQuantity(null, null)).isFalse();
    }

    @DisplayName("아이템 이름 max length 구하기 테스트")
    @Test
    void 아이템_이름_최대_길이_구하기_테스트() {
        String maxItemName = "123456";
        List<Item> items = List.of(
                new Item(1L, "12345", 1000, 5),
                new Item(1L, "1234", 1000, 5),
                new Item(1L, maxItemName, 1000, 5),
                new Item(1L, "126", 1000, 5)
        );

        assertThat(itemService.getItemNameMaxLength(items)).isEqualTo(maxItemName.length());
        assertThat(itemService.getItemNameMaxLength(null)).isLessThan(0);
    }

    @DisplayName("아이템 저장소 find by id in 조회")
    @Test
    void 아이템_저장소_find_by_id_in_조회_테스트() {
        Item firstItem = getFirstItemOrThrowException();
        Item secondItem = getSecondItemOrThrowException();
        List<Long> itemIds = List.of(
                firstItem.getId(),
                secondItem.getId()
        );

        List<Item> items = itemService.findByIdIn(itemIds);
        List<Item> misMatchitems = itemService.findByIdIn(
                List.of(
                    firstItem.getId(),
                    123123123124L
                )
        );
        List<Item> emptyItems = itemService.findByIdIn(List.of());

        assertThat(items.size()).isEqualTo(itemIds.size());
        assertThat(misMatchitems.size()).isEqualTo(1);
        assertThat(emptyItems.size()).isEqualTo(0);
    }


    public static Item getFirstItemOrThrowException() {
        Optional<Item> firstItem = itemService.findAll().stream()
                .findFirst();

        assertThatCode(() -> {
            firstItem.orElseThrow(() -> new NoSuchElementException());
        }).doesNotThrowAnyException();
        return firstItem.orElse(null);
    }

    public static Item getSecondItemOrThrowException() {
        itemService.findAll().stream().findFirst().get();
        assertThatCode(() -> {
            itemService.findAll().stream().findFirst().get();
        }).doesNotThrowAnyException();
        return itemService.findAll().stream().findFirst().get();
    }




}