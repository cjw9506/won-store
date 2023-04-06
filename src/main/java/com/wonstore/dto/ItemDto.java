package com.wonstore.dto;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private String itemName;

    private int itemPrice;

    private int itemQuantity;

    //일대일로 변경해야겠다. Item <-> itemImg
//    private ItemImg itemImg;
//
//    private List<ItemCategory> category;

    private String itemDetail;

    private String username;


}
