package com.wonstore.controller;

import com.wonstore.dto.mvcDto.OrderDto;
import com.wonstore.entity.Item;
import com.wonstore.entity.Member;
import com.wonstore.entity.Order;
import com.wonstore.exception.NotEnoughException;
import com.wonstore.service.ItemServiceImpl;
import com.wonstore.service.MemberServiceImpl;
import com.wonstore.service.OrderServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final MemberServiceImpl memberService;
    private final ItemServiceImpl itemService;
    private final OrderServiceImpl orderService;

    @GetMapping("/form")
    public String orderForm(@RequestParam Long itemId,
                              Model model,
                              OrderDto orderDto,
                              HttpSession session) {

        Member member = (Member) session.getAttribute("loginMember");
        model.addAttribute("member", member);

        Item item = itemService.findOne(itemId);
        model.addAttribute("item", item);

        //흐음...
        orderDto.setMemberName(member.getUserId());
        orderDto.setMemberEmail(member.getEmail());
        orderDto.setMemberPhoneNumber(member.getPhoneNumber());
        orderDto.setAddress(member.getAddress());
        orderDto.setItemName(item.getItemName());
        orderDto.setItemPrice(item.getItemPrice());
        orderDto.setItemQuantity(item.getItemQuantity());

        model.addAttribute("orderDto", orderDto);

        return "orders/orderForm";

    }

    @PostMapping("/form")
    public String order(@RequestParam Long itemId,
                        @RequestParam int count,
                        int totalPrice,
                        HttpSession session
                        ) throws NotEnoughException {


        Member member = (Member) session.getAttribute("loginMember");
        Long memberId = member.getId();

        orderService.order(memberId, itemId, count);

        return "redirect:/";
    }

    @GetMapping
    public String orderList(Model model) {
        List<Order> orders = orderService.orderList();
        model.addAttribute("orders", orders);

        return "orders/orderList";
    }

    @PostMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }



}
