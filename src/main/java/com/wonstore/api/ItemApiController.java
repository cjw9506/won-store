package com.wonstore.api;

import com.wonstore.config.data.MemberSession;
import com.wonstore.dto.apiDto.item.ItemResponse;
import com.wonstore.dto.apiDto.item.ItemRequest;
import com.wonstore.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping("/api/items/new")
    public Long createItem(@RequestBody ItemRequest request,
                                     MemberSession session) {
        Long itemId = itemService.saveItem(request);

        return itemId;
    }

    @GetMapping("/api/items")
    public List<ItemResponse> getItemList(MemberSession session) {

        return itemService.findItems();
    }

    @GetMapping("/api/items/{itemId}")
    public ItemResponse getOneItem(@PathVariable Long itemId,
                                   MemberSession session) {
        return itemService.findOneItem(itemId);
    }

    @PutMapping("api/items/{itemId}")
    public Long changeOneItem(@PathVariable Long itemId,
                              @RequestBody ItemRequest request,
                              MemberSession session) {
        return itemService.changeItem(itemId, request);
    }

    @DeleteMapping("api/items/{itemId}")
    public Long deleteOneItem(@PathVariable Long itemId,
                              MemberSession session) {
        return itemService.deleteItem(itemId);
    }
}
