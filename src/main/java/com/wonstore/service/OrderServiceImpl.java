package com.wonstore.service;

import com.wonstore.entity.*;
import com.wonstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService{

    private final MemberServiceImpl memberService;
    private final ItemServiceImpl itemService;
    private final OrderRepository orderRepository;

    @Override
    @Transactional //주문
    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberService.findOne(memberId);
        Item item = itemService.findOne(itemId);

        //배송정보 생성
        Delivery delivery = Delivery.builder()
                .address(member.getAddress())
                .status(DeliveryStatus.READY)
                .build();

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getItemPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);

        return order.getId();
    }


    @Override //전제 조회
    public List<Order> orderList() {
        List<Order> allOrder = orderRepository.findAll();
        return allOrder;
    }


    @Override //단건 조회
    public Order findOneById(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        return order;
    }

    @Override
    @Transactional //주문 취소
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.cancel();
    }

    @Override //페치 조인 조회
    public List<Order> findAllOrdersWithMemberAndDelivery() {
        return orderRepository.findAllWithMemberDelivery();
    }
}
