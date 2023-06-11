package com.example.itemorderapplication.domain;

import lombok.Getter;

@Getter
public class OrderItem {
    private Long itemId;
    private int quantity;

    public OrderItem(Long itemId,
                     int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void minusQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
