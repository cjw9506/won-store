package com.wonstore.api;

import com.wonstore.dto.apiDto.Result;
import com.wonstore.dto.apiDto.item.*;
import com.wonstore.entity.Item;
import com.wonstore.entity.Member;
import com.wonstore.service.ItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemApiController { //생성dto + 수정dto 합치기 -> 합쳤슴돠

    private final ItemServiceImpl itemService;

    @PostMapping("/api/items/new")//아이템 생성
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest request,
                                                  Member member) {
        Item item = Item.builder()
                .itemName(request.getItemName())
                .itemPrice(request.getItemPrice())
                .itemQuantity(request.getItemQuantity())
                .itemDetail(request.getItemDetail())
                .writer(member.getUserId()) //로그인 한 사용자 아이디 가져와야함 좀 있다 합시다. 일단 null
                .build();
        Long id = itemService.saveItem(item);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ItemResponse(id));
    }

    @GetMapping("/api/items") //전체 조회
    public Result items() {
        List<Item> findItems = itemService.findItems();
        List<ItemListDto> collect = findItems.stream()
                .map(i -> new ItemListDto(i.getItemName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/items/{id}") //단건 조회
    public Result item(@PathVariable("id") Long id) {
        Item item = itemService.findOne(id);
        return new Result(item);
    }

    @PutMapping("/api/items/edit/{id}") //아이템 수정
    public ItemResponse updateItem(@PathVariable("id") Long id,
                                         @RequestBody ItemRequest request) {
        Long itemId = itemService.findOne(id).getId();
        itemService.updateItem(itemId, request.getItemName(), request.getItemPrice(), request.getItemQuantity(), request.getItemDetail());

        return new ItemResponse(id);
    }

    @DeleteMapping("/api/items/{id}") //아이템 삭제
    public void deleteItem(@PathVariable("id") Long id) {
        itemService.delete(id);
    }

}
