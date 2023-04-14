package com.wonstore.api;

import com.wonstore.dto.apiDto.Result;
import com.wonstore.dto.apiDto.member.CreateMemberResponse;
import com.wonstore.dto.apiDto.review.*;
import com.wonstore.entity.Member;
import com.wonstore.entity.Review;
import com.wonstore.service.MemberServiceImpl;
import com.wonstore.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewServiceImpl reviewService;
    private final MemberServiceImpl memberService;

    @PostMapping("/api/review") //리뷰 생성
    public ResponseEntity<ReviewResponse> create(@RequestBody ReviewRequest request) {

        Review review = reviewService.createReview(
                request.getMemberId(),
                request.getItemId(),
                request.getTitle(),
                request.getContent());

        return ResponseEntity.created(URI.create("/api/reivew/" + review.getId())).body(new ReviewResponse(review.getId()));
    }

    @PutMapping("/api/review/{id}") //리뷰 수정
    public ReviewResponse update(@PathVariable("id") Long id,
                                 @RequestBody UpdateRequest request) {
        reviewService.updateReview(id, request.getTitle(), request.getContent());
        return new ReviewResponse(id);
    }

    @GetMapping("/api/review") //전체 리뷰 조회
    public Result reviews() {

        List<Review> reviews = reviewService.findReviews();
        List<Object> collect = reviews.stream()
                .map(r -> new ReviewList(r.getId(), r.getMember().getId(), r.getItem().getId(), r.getTitle(), r.getContent()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/review/{id}") //리뷰 단건 조회
    public ReviewList review(@PathVariable("id") Long id) {
        Review review = reviewService.findReview(id);
        ReviewList reviewList = new ReviewList(review.getId(),
                review.getMember().getId(),
                review.getItem().getId(),
                review.getTitle(),
                review.getContent());
        return reviewList;
    }

    @GetMapping("/api/review/members/{memberId}") //회원별 리뷰 조회
    public Result memberReview(@PathVariable("memberId") Long memberId) {
        List<Review> memberReview = reviewService.findMemberReview(memberId);
        List<MemberReviewList> collect = memberReview.stream()
                .map(m -> new MemberReviewList(m.getItem().getId(), m.getTitle(), m.getContent()))
                .collect(Collectors.toList());
        return new Result(collect);
    }
}
