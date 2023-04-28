package com.wonstore.api;

import com.wonstore.dto.apiDto.Result;
import com.wonstore.dto.apiDto.item.*;
import com.wonstore.entity.CategoryItem;
import com.wonstore.entity.Item;
import com.wonstore.entity.Member;
import com.wonstore.entity.Review;
import com.wonstore.service.ItemServiceImpl;
import com.wonstore.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemApiController { //생성dto + 수정dto 합치기 -> 합쳤슴돠

    private final ItemServiceImpl itemService;
    private final MemberServiceImpl memberService;

    @PostMapping("/api/items/new")//아이템 생성 - Admin
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest request) {

        Long id = itemService.saveItem(request.getItemName(), request.getItemPrice(), request.getItemQuantity(),
                request.getItemDetail(), request.getMemberId(), request.getCategoryId());

        return ResponseEntity.created(URI.create("/api/items/" + id)).body(new ItemResponse(id));
    }

    @GetMapping("/api/items") //전체 조회 - Admin, User
    public Result items() {
        List<Item> findItems = itemService.findItems();
        List<ItemListDto> collect = findItems.stream()
                .map(i -> new ItemListDto(i.getItemName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    //여기 수정해야함
    @GetMapping("/api/items/{itemId}") //단건 조회 - Admin, User
    public ItemDto item(@PathVariable("itemId") Long itemId) {
        Item item = itemService.findOne(itemId);
//        String name = null;
//        if (item.getCategoryItems().get(0).getCategory().getParent() != null) {
//            name = item.getCategoryItems().get(0).getCategory().getParent().getName() + " - " +
//                    item.getCategoryItems().get(0).getCategory().getName();
//        }
        List<String> reviews = new ArrayList<>();
        for (Review review : item.getReviews()) {
            reviews.add(review.getTitle());
        }
        ItemDto itemDto = new ItemDto(
                item.getId(),
                item.getItemName(),
                item.getItemPrice(),
                item.getItemQuantity(),
                item.getItemDetail(),
                item.getMemberId(),
                reviews
                );
        return itemDto;
    }

    @PutMapping("/api/items/edit/{itemId}") //아이템 수정 - Admin
    public ItemResponse updateItem(@PathVariable("itemId") Long itemId,
                                         @RequestBody ItemRequest request) {
        Long id = itemService.findOne(itemId).getId();
        itemService.updateItem(itemId, request.getItemName(), request.getItemPrice(), request.getItemQuantity(), request.getItemDetail());

        return new ItemResponse(id);
    }

    @DeleteMapping("/api/items/{itemId}") //아이템 삭제 - Admin
    public ResponseEntity<Map<String, String>> deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.delete(itemId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "상품이 삭제되었습니다.");

        return ResponseEntity.ok(response);
    }

}
