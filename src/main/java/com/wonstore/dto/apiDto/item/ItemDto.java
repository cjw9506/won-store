package com.wonstore.dto.apiDto.item;

import com.wonstore.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private Long id;
    private String itemName;
    private int itemPrice;
    private int itemQuantity;
    private String itemDetail;
    private String itemCategory;
    private String writer;
    private List<String> reviews;
    //private List<Review> reviews;
}
