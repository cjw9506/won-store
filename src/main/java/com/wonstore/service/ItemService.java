package com.wonstore.service;

import com.wonstore.entity.Item;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ItemService {

    //저장
    Long saveItem(Item item) throws IOException;

    //전체 조회
    List<Item> findItems();

    //단건 조회
    Item findOne(Long itemId);

    //수정
    void updateItem(Long id, String name, int price, int quantity, String detail);

    //삭제
    void delete(Long id);

}
