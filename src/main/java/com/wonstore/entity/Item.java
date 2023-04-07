package com.wonstore.entity;

import com.wonstore.dto.mvcDto.ItemDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private int itemPrice;

    private int itemQuantity;

    private String itemDetail;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    private String writer;

    public void setWriter(Member member) {
        this.writer = writer;
    }

    public void update(String itemName, int itemPrice, int itemQuantity, String itemDetail) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemDetail = itemDetail;
    }

    public void addStock(int quantity) {
        this.itemQuantity = itemQuantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.itemQuantity - quantity;
        if (restStock < 0) {
            throw new IllegalStateException("수량이 없습니다.");
        }
        this.itemQuantity = restStock;
    }

}
