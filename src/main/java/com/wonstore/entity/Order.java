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

@Entity
@Getter
@Builder
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //@Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //orderItem <-> order 연관관계 메서드
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.createOrder(this);
    }
    //delivery <-> order 연관관계 메서드
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.createDelivery(this);
    }

    //상태 업데이트
    public void changeStatus(OrderStatus status) {
        this.status = status;
    }

    //주문 생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) throws NotEnoughException {
        Order order = Order.builder() //주문 세팅
                .member(member)
                .status(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .orderItems(new ArrayList<>())
                .build();
        order.setDelivery(delivery); //배송 세팅
        for (OrderItem orderItem : orderItems) { //주문아이템 세팅
            order.addOrderItem(orderItem);
            //System.out.println(order.getOrderItems().get(0).getItem().getItemName());
        }

        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) { //주문아이템 * 수량 가격 세팅
            totalPrice += orderItem.getTotalPrice();
        }

        if (member.getCurrentPoint() < totalPrice) {
            throw new NotEnoughException("현재 포인트가 부족합니다.");
        }

        member.usePoint(totalPrice); //회원 현재포인트 삭감
        return order;
    }

    public static Order createCartOrder(Delivery delivery, Cart cart) throws NotEnoughException {
        Order order = Order.builder()
                .member(cart.getMember())
                .status(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .cart(cart)
                .orderItems(new ArrayList<>())
                .build();
        order.setDelivery(delivery);
        for (CartItem cartItem : cart.getCartItems()) {
            order.addOrderItem(OrderItem.createOrderItem(cartItem.getItem(), cartItem.getItem().getItemPrice(), cartItem.getCount()));
        }

        int totalPrice = 0;

        //주문아이템 * 수량 가격 세팅
        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += order.getTotalPrice();
        }

        if (cart.getMember().getCurrentPoint() < totalPrice) {
            throw new NotEnoughException("현재 포인트가 부족합니다.");
        }

        cart.getMember().usePoint(totalPrice); //회원 현재포인트 삭감
        return order;
    }

    //주문 취소
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.changeStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
        refundPoint();
    }

    private void refundPoint() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        member.addPoint(totalPrice);
    }

    // 총 구매가격
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
