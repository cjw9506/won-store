package com.wonstore.service;

import com.wonstore.entity.Category;

import java.util.List;

public interface CategoryService {

    Long addCategory(Category category);

    void updateParentCategory(Long categoryId, String name);

    void updateChildCategory(Long parentId, Category child, String name);

    Category findOne(Long categoryId);

    List<Category> findCategories();
}
