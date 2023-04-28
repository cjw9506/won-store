package com.wonstore.service;

import com.wonstore.entity.Category;
import com.wonstore.entity.CategoryItem;
import com.wonstore.entity.Item;
import com.wonstore.entity.Member;
import com.wonstore.repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public  class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MemberService memberService;
    private final CategoryServiceImpl categoryService;

    @PostConstruct //아이템 샘플 데이터
    public void init() {

        Member member1 = memberService.findOne(1L);
        Member member2 = memberService.findOne(2L);

        Item item1 = Item.builder()
                .itemName("아이템1")
                .itemPrice(10000)
                .itemQuantity(100)
                .itemDetail("쌉니다~")
                .memberId(member1.getId())
                .build();

        Item item2 = Item.builder()
                .itemName("아이템2")
                .itemPrice(20000)
                .itemQuantity(50)
                .itemDetail("쌉니다~~~")
                .memberId(member2.getId())
                .build();

        itemRepository.save(item1);
        itemRepository.save(item2);

    }

    @Transactional//아이템 저장
    @Override
    public Long saveItem(String itemName, int itemPrice, int itemQuantity, String itemDetail, Long memberId, Long categoryId) {
        Category category = categoryService.findOne(categoryId);
        Member member = memberService.findOne(memberId);
        CategoryItem categoryItem = CategoryItem.createCategoryItem(category);
        Item createItem = Item.createItem(itemName, itemPrice, itemQuantity, itemDetail, member.getId(), categoryItem);
        itemRepository.save(createItem);

        log.info("{}상품을 {}님이 생성하였습니다.", itemName, member.getUserId());
        log.info("상품 가격 : {}, 상품수량 : {}, 상품카테고리 : {}", itemPrice, itemQuantity,
                categoryItem.getCategory().getParent().getName() + " - " + categoryItem.getCategory().getName());

        return createItem.getId();
    }


    @Transactional
    @Override //아이템 수정 - 카테고리도 수정할 수 있게 바꿀 것
    public void updateItem(Long id, String name, int price, int quantity, String detail) {
        Item findItem = itemRepository.findById(id).get();
        findItem.update(name, price, quantity, detail);

        log.info("{}상품 정보가 수정되었습니다.", findItem.getItemName());
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
    @Override //삭제
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }




}
