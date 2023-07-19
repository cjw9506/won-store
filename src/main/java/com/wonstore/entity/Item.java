package com.wonstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private String itemDetail;

    private int price;

    private int stockQuantity;

    @Builder
    public Item(String itemName, String itemDetail, int price, int stockQuantity) {
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void changeItem(String itemName, String itemDetail, int price, int stockQuantity) {
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.price = price;
        this.stockQuantity = stockQuantity;

    }

    public void addStock(int count) {
        this.stockQuantity += count;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new IllegalArgumentException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}

