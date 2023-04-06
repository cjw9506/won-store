package com.wonstore.controller;

import com.wonstore.dto.ItemDto;
import com.wonstore.entity.Item;
import com.wonstore.entity.Member;
import com.wonstore.service.ItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemServiceImpl itemService;

    @GetMapping("/add") //인터셉터 관리
    public String item(Model model) {
        Model itemDto = model.addAttribute("itemDto", new ItemDto());
        return "items/addItem";
    }

    @PostMapping("/add")
    public String createItem(ItemDto itemDto, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");
        String userId = loginMember.getUserId();

        Item item = Item.builder()
                .itemName(itemDto.getItemName())
                .itemPrice(itemDto.getItemPrice())
                .itemQuantity(itemDto.getItemQuantity())
                .itemDetail(itemDto.getItemDetail())
                .writer(userId)
                .build();
        itemService.saveItem(item);
        return "redirect:/items";
    }

    @GetMapping
    public String list(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");
        String userId = loginMember.getUserId();
        model.addAttribute("loginId", userId);
        System.out.println(userId);

        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, Model model) {


        Item item = itemService.findOne(itemId).get();
        model.addAttribute("itemDto", Item.entityToDto(item));
        return "items/editItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable("itemId") Long itemId,
                           @ModelAttribute("itemDto") ItemDto itemDto,
                           HttpServletRequest request,
                           Model model) {

        itemService.updateItem(itemId, itemDto.getItemName(),
                itemDto.getItemPrice(), itemDto.getItemQuantity(), itemDto.getItemDetail());
        return "redirect:/";
    }

    @GetMapping("/{itemId}")
    public String itemDetail(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId).get();
        model.addAttribute("itemDto", Item.entityToDto(item));
        return "items/itemDetail";
    }

    @PostMapping("/{itemId}")
    public String orderItem(@PathVariable("itemId") Long itemId) {
        return "redirect:/orders/form?itemId=" + itemId;
    }
}
