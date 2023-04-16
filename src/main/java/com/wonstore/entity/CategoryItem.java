package com.wonstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category_item")
public class CategoryItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public static CategoryItem createCategoryItem(Category category) {
        CategoryItem categoryItem = CategoryItem.builder()
                .category(category)
                .build();
        return categoryItem;
    }

    public void createItem(Item item) {
        this.item = item;
    }
}
