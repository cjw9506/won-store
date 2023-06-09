package com.wonstore.service;

import com.wonstore.entity.Cart;

import java.util.List;

public interface CartService {

    Long createCart(Long memberId);

    void addCartItem(Long cartId, Long itemId, int count);

    Cart findCart(Long cartId);

    List<Cart> findAllCart();

    void updateCartItemCount(Long cartId, int cartItemId, int count);

    void removeCartItem(Long cartId, int cartItemId);
}
