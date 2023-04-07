package com.wonstore.service;

import com.wonstore.entity.*;
import com.wonstore.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.BorderUIResource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired OrderServiceImpl orderService;
    @Autowired MemberServiceImpl memberService;
    @Autowired ItemServiceImpl itemService;
    @Autowired OrderRepository orderRepository;

    @Test
    @Transactional
    public void order() {
        Member member = memberService.findOne(1L).get();
        Item item = itemService.findOne(1L).get();
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), 2);

        Order getOrder = orderRepository.findById(orderId).get();

        org.assertj.core.api.Assertions.assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
        System.out.println(getOrder.getTotalPrice() == 20000);
    }


}