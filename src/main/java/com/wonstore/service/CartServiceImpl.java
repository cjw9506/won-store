package com.wonstore.service;

import com.wonstore.entity.Cart;
import com.wonstore.entity.CartItem;
import com.wonstore.entity.Item;
import com.wonstore.entity.Member;
import com.wonstore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final MemberServiceImpl memberService;
    private final ItemServiceImpl itemService;

    @Transactional
    @Override //장바구니 생성
    public Long createCart(Long memberId) {
        Member member = memberService.findOne(memberId);
        Cart cart = Cart.createCart(member);
        cartRepository.save(cart);

        log.info("{}번째 장바구니가 {}님에게 개설되었습니다.", cart.getId(),member.getUserId());

        return cart.getId();
    }

    @Transactional
    @Override //장바구니에 아이템 추가
    public void addCartItem(Long cartId, Long itemId, int count) {

        Item item = itemService.findOne(itemId);
        Cart cart = cartRepository.findById(cartId).get();

        CartItem cartItem = CartItem.createCartItem(item, count);
        cart.addCartItem(cartItem);

        log.info("{}님이 장바구니의 {}상품을 {}개 담았습니다.", cart.getMember().getUserId(), cartItem.getItem().getItemName(), cartItem.getCount());
    }

    @Transactional
    public void updateCartItemCount(Long cartId, int cartItemId, int count) {
        Cart cart = cartRepository.findById(cartId).get();
        CartItem cartItem = cart.getCartItems().get(cartItemId - 1);
        cartItem.updateCount(count);
        log.info("{}님의 장바구니의 {}상품의 수량이 {}개로 변경되었습니다.", cart.getMember().getUserId(), cartItem.getItem().getItemName(), count);
    }

    @Transactional
    public void removeCartItem(Long cartId, int cartItemId) {
        Cart cart = cartRepository.findById(cartId).get();
        CartItem cartItem = cart.getCartItems().get(cartItemId-1);
        log.info("{}님의 장바구니에서 {} 상품이 삭제되었습니다.", cart.getMember().getUserId(), cartItem.getItem().getItemName());
        cart.removeCartItem(cartItem);

    }
    // 장바구니 아이템 하나씩 지우기 만들기!
//    @Transactional
//    public void removeCartItem(Long cartId, CartItem cartItem) {
//        Cart cart = cartRepository.findById(cartId).get();
//        cart.removeCartItem(cartItem);
//    }

    //단건 조회
    @Override
    public Cart findCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).get();
        return cart;
    }

    //전체 조회
    @Override
    public List<Cart> findAllCart() {
        List<Cart> all = cartRepository.findAll();
        return all;
    }

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
