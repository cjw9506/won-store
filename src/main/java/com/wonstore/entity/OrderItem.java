package com.wonstore.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int price;
    private int count;

    @Builder
    public OrderItem(Item item, int price, int count) {
        this.item = item;
        this.price = price;
        this.count = count;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getTotalPrice() {
        return getPrice() * getCount();
    }

    public void cancel() {
        getItem().addStock(count);
    }
}
