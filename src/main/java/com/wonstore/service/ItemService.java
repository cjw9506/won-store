package com.wonstore.service;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.wonstore.dto.apiDto.item.ItemRequest;
import com.wonstore.dto.apiDto.item.ItemResponse;
import com.wonstore.entity.Item;
import com.wonstore.exception.ItemNotFound;
import com.wonstore.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;


    @Transactional
    public Long saveItem(ItemRequest request) {

        Item item = Item.builder()
                .itemName(request.getItemName())
                .itemDetail(request.getItemDetail())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();

        itemRepository.save(item);

        return item.getId();
    }

    public List<ItemResponse> findItems() {

        return itemRepository.findAll().stream()
                .map(i -> ItemResponse.builder()
                        .id(i.getId())
                        .itemName(i.getItemName())
                        .itemDetail(i.getItemDetail())
                        .price(i.getPrice())
                        .stockQuantity(i.getStockQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    public ItemResponse findOneItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFound::new);

        ItemResponse response = ItemResponse.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .itemDetail(item.getItemDetail())
                .price(item.getPrice())
                .stockQuantity(item.getStockQuantity())
                .build();

        return response;
    }

    @Transactional
    public Long changeItem(Long itemId, ItemRequest request) {
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFound::new);

        item.changeItem(request.getItemName(), request.getItemDetail(), request.getPrice(), request.getStockQuantity());

        return item.getId();
    }

    @Transactional
    public Long deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);

        return itemId;
    }
}
