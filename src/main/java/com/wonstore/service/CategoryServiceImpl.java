package com.wonstore.service;

import com.wonstore.entity.Category;
import com.wonstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Transactional //카테고리 생성
    @Override
    public Long addCategory(Category category) {
        Category addCategory = categoryRepository.save(category);

        log.info("카테고리 {}가 생성되었습니다.", addCategory.getName());

        return addCategory.getId();
    }

    @Transactional //부모 카테고리 수정
    @Override
    public void updateParentCategory(Long categoryId, String name) {
        Category category = categoryRepository.findById(categoryId).get();
        category.updateParent(name);

        log.info("카테고리 {}가 {}로 수정되었습니다.", category.getName(), name);
    }
    @Transactional //자식 카테고리 수정
    @Override
    public void updateChildCategory(Long parentId, Category child, String name) {
        child.updateChild(name, parentId);

        log.info("카테고리 {}가 {}로 수정되었습니다.", child.getName(), name);
    }

    //단건조회
    @Override
    public Category findOne(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        return category;
    }
    //전체조회
    @Override
    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    @Transactional //삭제
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
