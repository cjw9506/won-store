package com.wonstore.service;

import com.wonstore.entity.Category;
import com.wonstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Transactional //카테고리 생성
    public Long addCategory(Category category) {
        Category addCategory = categoryRepository.save(category);
        return addCategory.getId();
    }

    @Transactional //부모 카테고리 수정
    public void updateParentCategory(Long categoryId, String name) {
        Category category = categoryRepository.findById(categoryId).get();
        category.updateParent(name);
    }
    @Transactional //자식 카테고리 수정
    public void updateChildCategory(Long parentId, Long childId, String name) {
        Category child = categoryRepository.findById(childId).get();
        child.updateChild(name, parentId);
    }

    //단건조회
    public Category findOne(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        return category;
    }
    //전체조회
    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    @Transactional //삭제
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
