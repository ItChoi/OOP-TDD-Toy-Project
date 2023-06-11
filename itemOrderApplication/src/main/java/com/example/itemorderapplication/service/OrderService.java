package com.example.itemorderapplication.service;

import com.example.itemorderapplication.common.Const;
import com.example.itemorderapplication.common.exception.SoldOutException;
import com.example.itemorderapplication.common.util.ConsoleUtil;
import com.example.itemorderapplication.common.util.PriceUtil;
import com.example.itemorderapplication.domain.Item;
import com.example.itemorderapplication.domain.Order;
import com.example.itemorderapplication.domain.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class OrderService {
    private static ItemService itemService = new ItemService();


    public void order(Order order) {
        if (!isAvailableOrder(order)) {
            return;
        }

        // 주문 진행중인 아이템 - 주문 시점에 아이템 재고 수량이 남아 있지 않은 경우 회복하기 위한 변수 (key: itemId, value: quantity)
        Map<Long, Integer> OrderingItemQuantityWithItemId = new HashMap<>();

        Map<Long, Item> itemsWithItemId = itemService.findAllWithItemId();
        Map<Long, OrderItem> orderItemsWithItemId = order.getOrderItemsWithItemId();
        boolean isException = false;
        String exceptionItemName = "";
        for (Long itemId : orderItemsWithItemId.keySet()) {
            OrderItem orderItem = orderItemsWithItemId.get(itemId);
            Item savedItem = itemsWithItemId.get(itemId);

            int orderItemQuantity = orderItem.getQuantity();
            if (savedItem.getStockQuantity() >= orderItemQuantity) {
                // 구매 가능
                savedItem.minusStockQuantity(orderItemQuantity);
                OrderingItemQuantityWithItemId.put(
                        itemId,
                        OrderingItemQuantityWithItemId.getOrDefault(itemId, 0) + orderItemQuantity
                );

            } else {
                // 솔드아웃 익셉션
                isException = true;
                exceptionItemName = savedItem.getName();
                break;
            }
        }

        if (isException) {
            restoreFailedOrderItem(OrderingItemQuantityWithItemId);
            throw new SoldOutException(exceptionItemName);
        }

        showOrderHistory(order);
    }

    private void showOrderHistory(Order order) {
        Map<Long, Item> itemsWithItemId = itemService.findAllWithItemId();

        int totalPrice = 0;

        System.out.println("주문내역:");
        System.out.println("---------------------------------------");
        Map<Long, OrderItem> orderItemsWithItemId = order.getOrderItemsWithItemId();
        for (Long itemId : orderItemsWithItemId.keySet()) {
            OrderItem orderItem = orderItemsWithItemId.get(itemId);
            Item savedItem = itemsWithItemId.get(itemId);
            System.out.println(savedItem.getName() + " - " + orderItem.getQuantity() + "개");

            totalPrice += savedItem.getPrice() * orderItem.getQuantity();
        }

        showConsoleOrderHistory(totalPrice);
    }

    private static void showConsoleOrderHistory(int totalPrice) {
        int deliveryFee = totalPrice < Const.MINIMUM_PRICE_DELIVERY_FEE ? Const.DELIVERY_FEE : 0;

        System.out.println("---------------------------------------");
        System.out.println("주문 금액: " + PriceUtil.formattedWonPrice(totalPrice));
        System.out.println("배송비: " + PriceUtil.formattedWonPrice(deliveryFee));

        System.out.println("---------------------------------------");
        System.out.println("지불금액: " + PriceUtil.formattedWonPrice(deliveryFee + totalPrice));
        System.out.println("---------------------------------------");
    }

    public boolean isAvailableOrder(Order order) {
        if (Objects.isNull(order)) {
            log.error("주문은 비어있을 수 없습니다.");
            return false;
        }

        boolean isAvailableOrder = !(Objects.isNull(order.getOrderItemsWithItemId()) || order.getOrderItemsWithItemId().size() == 0);
        if (!isAvailableOrder) {
            log.warn("주문을 하기 위해서는 아이템을 추가해주세요.");
        }

        return isAvailableOrder;
    }

    public void restoreFailedOrderItem(Map<Long, Integer> OrderingItemQuantityWithItemId) {
        Map<Long, Item> itemsWithItemId = itemService.findAllWithItemId();
        if (OrderingItemQuantityWithItemId.size() > 0) {
            for (Long itemId : OrderingItemQuantityWithItemId.keySet()) {
                Integer restoreQuantityForItem = OrderingItemQuantityWithItemId.get(itemId);
                itemsWithItemId.get(itemId).plusStockQuantity(restoreQuantityForItem);
            }
        }
    }

    public boolean hasOrderItem(Map<Long, OrderItem> orderItemsWithItemId) {
        boolean isAddedOrderItem = !Objects.isNull(orderItemsWithItemId) && orderItemsWithItemId.size() != 0;
        if (!isAddedOrderItem) {
            log.warn(Const.NOT_EXISTS_ADDED_ITEM_MENT);
        }

        return isAddedOrderItem;
    }

    public void showCurrentOrderItemBasket(Order order) {
        Map<Long, OrderItem> orderItemsWithItemId = order.getOrderItemsWithItemId();
        List<Long> itemIds = orderItemsWithItemId.keySet().stream()
                .collect(Collectors.toList());

        List<Item> items = itemService.findByIdIn(itemIds);
        itemService.showConsoleOrganizedAllOrderItems(items, orderItemsWithItemId);
    }

    public String quitOrder() {
        return Const.USER_ORDER_COMPLETE_THANK_MENT;
    }

    @Nullable
    public Long getItemIdInMyOrderWithInput(Order order) {
        if (Objects.isNull(order)) {
            return null;
        }

        String itemIdStr = ConsoleUtil.inputStrPrefix(Const.INPUT_ITEM_ID);
        if (!PriceUtil.isOnlyDigit(itemIdStr)) {
            return null;
        }

        Long itemId = Long.valueOf(itemIdStr);
        Map<Long, OrderItem> orderItemsWithItemId = order.getOrderItemsWithItemId();
        if (Objects.isNull(orderItemsWithItemId.get(itemId))) {
            return null;
        }

        return itemId;
    }


    @Nullable
    public Integer getDeleteQuantityInMyOrderWithInput(OrderItem orderItem) {
        String itemQuantityStr = ConsoleUtil.inputStrPrefix(Const.INPUT_ITEM_QUANTITY);
        if (!PriceUtil.isOnlyDigit(itemQuantityStr)) {
            return null;
        }

        int itemQuantity = Integer.valueOf(itemQuantityStr);
        if (orderItem.getQuantity() < itemQuantity) {
            log.warn("삭제 가능한 수량을 {} 이하로 입력해주세요.", orderItem.getQuantity());
            return null;
        }

        return itemQuantity;
    }

    public void minusOrderItemQuantity(Order order) {
        Map<Long, OrderItem> orderItemsWithItemId = order.getOrderItemsWithItemId();
        if (Objects.isNull(orderItemsWithItemId) || orderItemsWithItemId.size() == 0) {
            log.warn("주문한 상품이 존재하지 않습니다.");
            return;
        }

        Long itemId;
        while (Objects.isNull((itemId = getItemIdInMyOrderWithInput(order)))) {
            log.warn("주문한 상품번호를 입력해주세요.");
        }

        Integer quantity;
        while (Objects.isNull((quantity = getDeleteQuantityInMyOrderWithInput(orderItemsWithItemId.get(itemId))))) {
            log.warn("올바른 수량을 입력해주세요.");
        }

        order.minusOrderItemQuantity(itemId, quantity);
    }
}
