package com.wonstore.api;

import com.wonstore.dto.apiDto.Result;
import com.wonstore.dto.apiDto.cart.*;
import com.wonstore.dto.apiDto.order.OrderResponse;
import com.wonstore.entity.Cart;
import com.wonstore.entity.CartItem;
import com.wonstore.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor //카트에 cartItem 삭제 추가해야해!!!!!!
public class CartApiController {

    private final CartServiceImpl cartService;

    @PostMapping("/api/cart/new") //카트 생성
    public ResponseEntity<CartResponse> createCart(@RequestBody CartRequest request) {
        Long cartId = cartService.createCart(request.getMemberId());

        return ResponseEntity.created(URI.create("/api/cart/" + cartId)).body(new CartResponse(cartId));
    }

    @PostMapping("/api/cart/{cartId}") //카트에 cartItem 추가
    public ResponseEntity<CartResponse> addCartItem(@PathVariable("cartId") Long cartId, @RequestBody CartAddRequest request) {
        cartService.addCartItem(cartId, request.getItemId(), request.getCount());

        return ResponseEntity.created(URI.create("/api/cart/item/" + cartId)).body(new CartResponse(cartId));
    }

    @PutMapping("api/cart/{cartId}/items/{itemId}") //카트의 cartItem 수량 변경
    public CartResponse updateCartItemCount(@PathVariable("cartId") Long cartId,
                                            @PathVariable("itemId") int itemId ,
                                            @RequestBody CartUpdateRequest request) {
        cartService.updateCartItemCount(cartId, itemId, request.getCount());

        return new CartResponse(cartId);
    }

    @DeleteMapping("/api/cart/{cartId}/items/{itemId}")
    public ResponseEntity<Map<String, String>> removeCartItem(@PathVariable("cartId") Long cartId, @PathVariable("itemId") int cartItemId) {
        cartService.removeCartItem(cartId, cartItemId);

        Map<String, String> response = new HashMap<>();
        response.put("message","장바구니의 " + cartItemId + "번 상품이 삭제되었습니다.");

        return ResponseEntity.ok(response);
    }

//    @PutMapping("/api/cart/{cartId}")
//    public CartResponse removeCartItem(@PathVariable("cartId") Long cartId, @RequestBody CartRemoveRequest request) {
//        Cart cart = cartService.findCart(cartId);
//        CartItem cartItem = cart.getCartItems().get(request.getCartItemId() - 1);
//        cart.removeCartItem(cartItem);
//        cartItem = null;
//        return new CartResponse(cartId);
//    }

    @GetMapping("/api/cart")//전체 조회
    public Result findCarts() {
        List<Cart> allCart = cartService.findAllCart();
        List<CartList> collect = allCart.stream().map(c -> {
                    List<Long> itemIds = c.getCartItems().stream().map(ci -> ci.getItem().getId()).collect(Collectors.toList());
                    List<Integer> counts = c.getCartItems().stream().map(CartItem::getCount).collect(Collectors.toList());
                    return new CartList(c.getId(), c.getMember().getId(), itemIds, counts);
                }).sorted(Comparator.comparing(CartList::getCartId))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/cart/{cartId}") //단건 조회
    public CartList findCart(@PathVariable("cartId") Long cartId) {
        Cart cart = cartService.findCart(cartId);
        List<Long> itemIds = cart.getCartItems().stream().map(ci -> ci.getItem().getId()).collect(Collectors.toList());
        List<Integer> counts = cart.getCartItems().stream().map(CartItem::getCount).collect(Collectors.toList());
        return new CartList(cart.getId(), cart.getMember().getId(), itemIds, counts);
    }

    @DeleteMapping("/api/cart/{cartId}") //이게 쓰일까...? 카트 삭제는 안쓰일듯?
    public void deleteCart(@PathVariable("cartId") Long cartId) {
        cartService.deleteCart(cartId);
    }
}
