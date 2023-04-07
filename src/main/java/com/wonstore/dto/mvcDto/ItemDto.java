package com.wonstore.dto.mvcDto;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private String itemName;

    private int itemPrice;

    private int itemQuantity;

    private String itemDetail;

    private String username;


}
