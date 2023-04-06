package com.wonstore.entity;

import com.wonstore.dto.ItemDto;
import com.wonstore.dto.MemberDto;
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

    public static ItemDto entityToDto(Item item) {
        ItemDto dto = ItemDto.builder()
                .itemName(item.getItemName())
                .itemPrice(item.getItemPrice())
                .itemQuantity(item.getItemQuantity())
                .itemDetail(item.getItemDetail())
                .username(item.getWriter())
                .build();
        return dto;
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
