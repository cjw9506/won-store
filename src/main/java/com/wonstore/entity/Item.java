package com.wonstore.entity;

import com.wonstore.exception.NotEnoughException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    private Long memberId;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();

    public static Item createItem(String itemName, int price, int quantity, String itemDetail, Long memberId, CategoryItem... categoryItems) {
        Item item = Item.builder()
                .itemName(itemName)
                .itemPrice(price)
                .itemQuantity(quantity)
                .itemDetail(itemDetail)
                .categoryItems(new ArrayList<>())
                .memberId(memberId)
                .build();
        for (CategoryItem categoryItem : categoryItems) {
            item.addCategoryItem(categoryItem);
        }
        return item;
    }

    public void addCategoryItem(CategoryItem categoryItem) {
        categoryItems.add(categoryItem);
        categoryItem.createItem(this);
    }

    public void update(String itemName, int itemPrice, int itemQuantity, String itemDetail) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemDetail = itemDetail;
    }

    public void addStock(int quantity) {
        this.itemQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.itemQuantity - quantity;
        if (restStock < 0) {
            throw new IllegalStateException("수량이 없습니다.");
        }
        this.itemQuantity = restStock;
    }

}
