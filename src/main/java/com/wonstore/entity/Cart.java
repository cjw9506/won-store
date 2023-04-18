package com.wonstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "cart")
    private List<Order> orders = new ArrayList<>();

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.createCart(this);
    }


    public static Cart createCart(Member member, CartItem... cartItems) {
        Cart cart = Cart.builder()
                .member(member)
                .build();

        for (CartItem cartItem : cartItems) {//장바구니 아이템 추가
            cart.addCartItem(cartItem);
        }

        return cart;

    }

    public int calculateTotalPrice() {
        int totalPrice = 0;

        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getCount() * cartItem.getItem().getItemPrice();
        }
        return totalPrice;
    }






}
