package com.wonstore.service;

import com.wonstore.entity.Item;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ItemService {

    Long saveItem(Item item) throws IOException;

    List<Item> findItems();

    Optional<Item> findOne(Long itemId);

    void updateItem(Long id, String name, int price, int quantity, String detail);

}
