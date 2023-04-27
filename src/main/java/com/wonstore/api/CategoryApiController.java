package com.wonstore.api;

import com.wonstore.dto.apiDto.Result;
import com.wonstore.dto.apiDto.category.*;
import com.wonstore.dto.apiDto.member.CreateMemberResponse;
import com.wonstore.dto.apiDto.member.MemberDto;
import com.wonstore.entity.Category;
import com.wonstore.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CategoryApiController {

    private final CategoryServiceImpl categoryService;

    @PostMapping("/api/category/parent") //부모 카테고리 생성
    public ResponseEntity<CategoryResponse> createParentCategory(@RequestBody CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .build();
        Long id = categoryService.addCategory(category);

        log.info("상위 카테고리 {} (이/가) 생성되었습니다.", category.getName());

        return ResponseEntity.created(URI.create("/api/category/parent/" + id)).body(new CategoryResponse(id));
    }

    @PostMapping("/api/category/child") //자식 카테고리 생성
    public ResponseEntity<CategoryResponse> createChildCategory(@RequestBody ChildRequest request) {
        Category parent = categoryService.findOne(request.getParentId());
        Category child = Category.builder()
                .name(request.getName())
                .build();
        parent.addChildCategory(child);
        Long id = categoryService.addCategory(parent);

        log.info("상위 카테고리 {}의 하위 카테고리 {}(이/가) 생성되었습니다.", parent.getName(), child.getName());

        return ResponseEntity.created(URI.create("/api/category/child/" + id)).body(new CategoryResponse(id));
    }

    @PutMapping("/api/category/parent/{categoryId}") //부모 카테고리 수정
    public CategoryResponse updateParentCategory(@PathVariable("categoryId") Long categoryId,
                                                 @RequestBody CategoryRequest request) {

        Category category = categoryService.findOne(categoryId);
        String currentCategoryName = category.getName();
        categoryService.updateParentCategory(categoryId, request.getName());

        log.info("상위 카테고리 {}가 {}로 수정되었습니다.", currentCategoryName, request.getName());

        return new CategoryResponse(categoryId);
    }

    //흐음 나중에 다시 살펴보자
    @PutMapping("/api/category/child/{childId}") //자식 카테고리 수정
    public CategoryResponse updateChildCategory(@PathVariable("childId") Long childId,
                                                @RequestBody UpdateChildRequest request) {
        Category child = categoryService.findOne(childId); //자식 카테고리 조회
        Category parent = child.getParent(); //부모 카테고리 조회
        parent.removeChildCategory(child); //부모 카테고리에서 자식 카테고리 제거

        Category newParent = categoryService.findOne(request.getNewParentId()); //새로운 부모 카테고리 조회
        categoryService.updateChildCategory(request.getNewParentId(), child, request.getName());
        newParent.addChildCategory(child);
        categoryService.addCategory(newParent);

        log.info("상위 카테고리 {}의 하위카테고리 {}(이/가) 상위카테고리 {}의 하위카테고리 {}로 변경되었습니다.", parent.getName(), child.getName(), newParent.getName(), request.getName());

        return new CategoryResponse(childId);
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

    @DeleteMapping("/api/category/{categoryId}") //카테고리 삭제
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.delete(categoryId);

        Map<String, String> response = new HashMap<>();
        response.put("message","카테고리가 삭제되었습니다.");

        return ResponseEntity.ok(response);
    }
}
