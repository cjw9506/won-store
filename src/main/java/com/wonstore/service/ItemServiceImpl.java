package com.wonstore.service;

import com.wonstore.entity.Item;
import com.wonstore.entity.Member;
import com.wonstore.repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public  class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MemberService memberService;

    @PostConstruct //아이템 샘플 데이터
    public void init() {

        Member member1 = memberService.findOne(1L);
        Member member2 = memberService.findOne(2L);

        Item item1 = Item.builder()
                .itemName("아이템1")
                .itemPrice(10000)
                .itemQuantity(100)
                .itemDetail("쌉니다~")
                .writer(member1.getUserId())
                .build();

        Item item2 = Item.builder()
                .itemName("아이템2")
                .itemPrice(20000)
                .itemQuantity(50)
                .itemDetail("쌉니다~~~")
                .writer(member2.getUserId())
                .build();

        itemRepository.save(item1);
        itemRepository.save(item2);

    }

    @Transactional
    @Override //아이템 저장
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }


    @Transactional
    @Override //아이템 수정
    public void updateItem(Long id, String name, int price, int quantity, String detail) {
        Item findItem = itemRepository.findById(id).get();
        findItem.update(name, price, quantity, detail);
    }

    @Override //전체 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    @Override //단건 조회
    public Item findOne(Long itemId) {
        return itemRepository.findById(itemId).get();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }




}
