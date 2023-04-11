package com.wonstore.dto.apiDto.order;

import com.wonstore.entity.Order;
import com.wonstore.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderListDto {
    private String id;
    private List<OrderItem> orderItems;

    public OrderListDto(Order order) {
        id = order.getMember().getUserId();
        orderItems = order.getOrderItems();
    }
}
