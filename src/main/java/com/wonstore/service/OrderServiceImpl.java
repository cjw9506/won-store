package com.wonstore.service;

import com.wonstore.entity.*;
import com.wonstore.exception.NotEnoughException;
import com.wonstore.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final MemberServiceImpl memberService;
    private final ItemServiceImpl itemService;
    private final OrderRepository orderRepository;
    private final CartServiceImpl cartService;

    @Override
    @Transactional //주문
    public Long order(Long memberId, Long itemId, int count) throws NotEnoughException {

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

        log.info("주문이 생성되었습니다.");
        log.info("주문 고객 : {}", order.getMember().getUserId());
        log.info("주문번호 : {}, 주문 아이템 : {}, 주문 수량 : {}", order.getId(), orderItem.getItem().getItemName(), orderItem.getCount());

        return order.getId();
    }

    @Transactional
    @Override
    public Long cartOrder(Long cartId) throws NotEnoughException{

        Cart cart = cartService.findCart(cartId);

        Delivery delivery = Delivery.builder()
                .address(cart.getMember().getAddress())
                .status(DeliveryStatus.READY)
                .build();

        Order order = Order.createCartOrder(delivery, cart);
        orderRepository.save(order);

        log.info("주문이 생성되었습니다.");
        log.info("주문 고객 : {}", order.getMember().getUserId());
        log.info("주문번호 : {}, 주문 아이템 : {}, 주문 수량 : {}", order.getId(),
                cart.getCartItems().stream().map(cartItem -> cartItem.getItem().getItemName()).collect(Collectors.toList()),
                cart.getCartItems().stream().map(CartItem::getCount).collect(Collectors.toList()));

        return order.getId();
    }


    @Override //전체 조회
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

        log.info("주문넘버 {} 가 주문 취소되었습니다.", order.getId());
    }

    @Override //페치 조인 조회
    public List<Order> findAllOrdersWithMemberAndDelivery() {
        return orderRepository.findAllWithMemberDelivery();
    }
}
