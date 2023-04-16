package com.wonstore.api;

import com.wonstore.dto.apiDto.Result;
import com.wonstore.dto.apiDto.category.*;
import com.wonstore.dto.apiDto.member.CreateMemberResponse;
import com.wonstore.dto.apiDto.member.MemberDto;
import com.wonstore.entity.Category;
import com.wonstore.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CategoryApiController {

    private final CategoryServiceImpl categoryService;

    @PostMapping("/api/category/parent") //부모 카테고리 생성
    public ResponseEntity<CategoryResponse> createParentCategory(@RequestBody CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .build();
        Long id = categoryService.addCategory(category);
        return ResponseEntity.created(URI.create("/api/category/parent" + id)).body(new CategoryResponse(id));
    }

    @PostMapping("/api/category/child") //자식 카테고리 생성
    public ResponseEntity<CategoryResponse> createChildCategory(@RequestBody ChildRequest request) {
        Category parent = categoryService.findOne(request.getParentId());
        Category child = Category.builder()
                .name(request.getName())
                .build();
        parent.addChildCategory(child);
        Long id = categoryService.addCategory(parent);

        return ResponseEntity.created(URI.create("/api/category/child" + id)).body(new CategoryResponse(id));
    }

    @PutMapping("/api/category/parent/{id}") //부모 카테고리 수정
    public CategoryResponse updateParentCategory(@PathVariable("id") Long id,
                                                 @RequestBody CategoryRequest request) {

        Category category = categoryService.findOne(id);
        System.out.println("category.getName() = " + category.getName());
        categoryService.updateParentCategory(id, request.getName());
        System.out.println("category.getName() = " + category.getName());
        return new CategoryResponse(id);
    }

    @PutMapping("/api/category/child/{id}") //자식 카테고리 수정
    public CategoryResponse updateChildCategory(@PathVariable("id") Long id,
                                                @RequestBody UpdateChildRequest request) {
        Category parent = categoryService.findOne(request.getParentId());
        Category child = categoryService.findOne(id);
        parent.removeChildCategory(child);

        Category newParent = categoryService.findOne(request.getNewParentId());
        categoryService.updateChildCategory(request.getNewParentId(), id, request.getName());
        Category newChild = categoryService.findOne(id);
        newParent.addChildCategory(newChild);
        return new CategoryResponse(id);
    }

    @GetMapping("/api/categories") //전체 조회
    public Result categories() {
        List<Category> categories = categoryService.findCategories();
        List<CategoryList> collect = categories.stream()
                .map(c -> {
                    Long parentId = null;
                    if (c.getParent() != null) {
                        parentId = c.getParent().getId();
                    }
                    return new CategoryList(c.getId(), c.getName(), parentId);
                })
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/category/{id}") //단건 조회
    public CategoryDto category(@PathVariable("id") Long id) {
        Category category = categoryService.findOne(id);
        Long parentId = null;
        String parentName = null;
        if (category.getParent() != null) {
            parentId = category.getParent().getId();
            parentName = category.getParent().getName();
        }
        CategoryDto categoryDto = new CategoryDto(
                category.getId(),
                category.getName(),
                parentId,
                parentName
        );
        return categoryDto;

    }

    @DeleteMapping("/api/category/{id}") //카테고리 삭제
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
    }
}
