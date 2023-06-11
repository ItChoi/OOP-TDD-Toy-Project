package com.example.itemorderapplication.service;

import com.example.itemorderapplication.common.Const;
import com.example.itemorderapplication.common.enumeration.ErrorType;
import com.example.itemorderapplication.common.util.ConsoleUtil;
import com.example.itemorderapplication.common.util.PriceUtil;
import com.example.itemorderapplication.domain.Item;
import com.example.itemorderapplication.domain.OrderItem;
import com.example.itemorderapplication.repository.InitDataReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class ItemService {

    public Optional<Item> findById(Long itemId) {
        if (Objects.isNull(itemId)) {
            return Optional.empty();
        }

        return Optional.ofNullable(findAllWithItemId().get(itemId));
    }

    public Item findByIdOrThrow(Long itemId) {
        if (Objects.isNull(itemId)) {
            throw new IllegalArgumentException(ErrorType.NOT_INPUT_REQUIRED_DATA.getMsg());
        }

        return findById(itemId)
                .orElseThrow(() -> new NoSuchElementException(ErrorType.NOT_FOUND_REQUIRED_Object.getMsg()));
    }

    public ConcurrentHashMap<Long, Item> findAllWithItemId() {
        return new ConcurrentHashMap<>(findAll().stream()
                .collect(Collectors.toMap(Item::getId, Function.identity())));
    }
    public ConcurrentLinkedQueue<Item> findAll() {
        return InitDataReader.itemDatas;
    }

    // Scanner 문자 입력 처리 메소드
    public boolean isRightItemId(String itemId) {
        if (!StringUtils.hasText(itemId) || !PriceUtil.isOnlyDigit(itemId) || "0".equals(itemId)) {
            return false;
        }

        return isRightItemId(Long.valueOf(itemId));
    }

    public boolean isRightItemId(Long itemId) {
        if (Objects.isNull(itemId)) {
            return false;
        }

        return !findById(itemId).isEmpty();
    }

    public boolean canPurchaseItemQuantity(Long itemId,
                                           String itemQuantity) {
        if (!PriceUtil.isOnlyDigit(itemQuantity)) {
            return false;
        }

        return canPurchaseItemQuantity(itemId, Integer.parseInt(itemQuantity));
    }

    public boolean canPurchaseItemQuantity(Long itemId,
                                           int itemQuantity) {

        if (!isRightItemId(itemId) || itemQuantity <= 0) {
            return false;
        }

        int itemStockQuantity = findByIdOrThrow(itemId).getStockQuantity();
        return itemStockQuantity >= itemQuantity;
    }

    // 이용자 q 입력 -> null 반환 -> 이용자 주문 종료 (주문에 쌓인 아이템 전부 클리어)
    @Nullable
    public Long getCorrectItemIdWithInput() {
        String itemIdStr;
        while (!isRightItemId(itemIdStr = ConsoleUtil.inputStrPrefix(Const.INPUT_ITEM_ID))) {
            if (ConsoleUtil.isFinishConsole(itemIdStr)) {
                log.warn("이용자가 상품번호 입력 중 주문을 종료했습니다.");
                return null;
            }

            if (Const.ORDER_COMPLETE.equals(itemIdStr)) {
                return 0L;
            }

            log.warn("올바른 상품번호를 입력해주세요.");
        }
        return findByIdOrThrow(Long.valueOf(itemIdStr)).getId();
    }

    // 이용자 q 입력 -> null 반환 -> 이용자 주문 종료 (주문에 쌓인 아이템 전부 클리어)
    @Nullable
    public Integer getCorrectItemQuantityWithInput(Long itemId) {
        String itemQuantityStr;
        while (!canPurchaseItemQuantity(itemId, itemQuantityStr = ConsoleUtil.inputStrPrefix(Const.INPUT_ITEM_QUANTITY))) {
            if (ConsoleUtil.isFinishConsole(itemQuantityStr)) {
                log.warn("이용자가 상품 수량 입력 중 주문을 종료했습니다.");
                return null;
            }

            if (Const.ORDER_COMPLETE.equals(itemQuantityStr)) {
                return 0;
            }

            log.warn("올바른 수량을 입력해주세요.");
        }

        return Integer.parseInt(itemQuantityStr);
    }

    public void showConsoleOrganizedAllItems() {
        showConsoleOrganizedAllItems(findAll());
    }

    public void showConsoleOrganizedAllItems(Collection<Item> items) {
        if (Objects.isNull(items) || items.size() == 0) {
            return;
        }

        int itemNameMaxLength = getItemNameMaxLength(items);
        System.out.printf("%-6s", "상품번호");
        System.out.printf("%-" + (itemNameMaxLength) + "s", "상품명");
        System.out.printf("%" + (itemNameMaxLength) + "s", "판매가격");
        System.out.printf("%" + (itemNameMaxLength) + "s %n", "재고수");
        for (Item item : items) {
            System.out.printf("%-8d", item.getId());
            System.out.printf("%-" + (itemNameMaxLength) + "s", item.getName());
            System.out.printf("%" + (itemNameMaxLength) + "s", item.getPrice());
            System.out.printf("%" + (itemNameMaxLength) + "s %n", item.getStockQuantity());
        }
    }

    public void showConsoleOrganizedAllOrderItems(List<Item> items,
                                                  Map<Long, OrderItem> orderItemsWithItemId) {
        if (Objects.isNull(items) || items.size() == 0) {
            return;
        }

        int itemNameMaxLength = getItemNameMaxLength(items);
        System.out.printf("%-6s", "상품번호");
        System.out.printf("%-" + (itemNameMaxLength) + "s", "상품명");
        System.out.printf("%" + (itemNameMaxLength) + "s", "구매가격");
        System.out.printf("%" + (itemNameMaxLength) + "s", "수량");
        System.out.printf("%" + (itemNameMaxLength) + "s %n", "초과수량");
        for (Item item : items) {
            Long itemId = item.getId();
            OrderItem orderItem = orderItemsWithItemId.get(itemId);
            System.out.printf("%-8d", itemId);
            System.out.printf("%-" + (itemNameMaxLength) + "s", item.getName());
            int orderItemQuantity = orderItem.getQuantity();
            System.out.printf("%" + (itemNameMaxLength) + "s", (PriceUtil.formattedWonPrice(item.getPrice() * orderItemQuantity)));
            System.out.printf("%" + (itemNameMaxLength) + "s", orderItemQuantity);
            int exceedItemQuantity = orderItemQuantity - item.getStockQuantity() > 0 ? orderItemQuantity - item.getStockQuantity() : 0;
            System.out.printf("%" + (itemNameMaxLength) + "s %n", exceedItemQuantity);
        }
    }

    public int getItemNameMaxLength(Collection<Item> items) {
        int maxSize = Integer.MIN_VALUE;

        if (Objects.isNull(items) || items.size() == 0) {
            return maxSize;
        }

        for (Item item : items) {
            maxSize = Math.max(maxSize, item.getName().length());
        }

        return maxSize;
    }

    public List<Item> findByIdIn(Collection<Long> itemIds) {
        if (Objects.isNull(itemIds) || itemIds.size() == 0) {
            return List.of();
        }

        Map<Long, Item> itemWithItemId = findAllWithItemId();
        List<Item> items = new ArrayList<>();
        for (Long itemId : itemIds) {
            Item item = itemWithItemId.get(itemId);
            if (item != null) {
                items.add(item);
            }
        }

        if (items.size() != itemIds.size()) {
            log.warn(Const.MISMATCH_ITEM_COUNT);
        }

        return items;
    }

    public void monitoringExceedItemQuantity(Long itemId,
                                             int orderItemQuantity) {
        if (Objects.isNull(itemId) || itemId <= 0 || orderItemQuantity <= 0) {
            return;
        }

        if (findByIdOrThrow(itemId).getStockQuantity() < orderItemQuantity) {
            log.warn("상품 재고량보다 주문 수량이 초과 됐습니다. 장바구니를 통해 확인 후 주문 수량을 조절해주세요.");
        }
    }
}
