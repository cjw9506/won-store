package com.wonstore.service;

import com.wonstore.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemServiceImplTest {

    @Autowired ItemServiceImpl itemService;

    @Test
    public void saveItem() {
        Item item = Item.builder().itemName("test Item").build();


        Item findItem = itemService.findOne(item.getId());

        org.assertj.core.api.Assertions.assertThat(findItem.getItemName()).isEqualTo(item.getItemName());


    }
    @Test
    public void test2() {
        Item item = itemService.findOne(1L);

        int itemPrice = item.getItemPrice();
        System.out.println(itemPrice);

    }

}