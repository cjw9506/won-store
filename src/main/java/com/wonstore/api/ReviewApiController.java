package com.wonstore.api;

import com.wonstore.dto.apiDto.Result;
import com.wonstore.dto.apiDto.review.*;
import com.wonstore.entity.Review;
import com.wonstore.service.MemberServiceImpl;
import com.wonstore.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReviewApiController { //상품별 리뷰 조회도 만들어야할듯;

    private final ReviewServiceImpl reviewService;
    private final MemberServiceImpl memberService;

    @PostMapping("/api/reviews/new") //리뷰 생성
    public ResponseEntity<ReviewResponse> create(@RequestBody ReviewRequest request) {

        Review review = reviewService.createReview(
                request.getMemberId(),
                request.getItemId(),
                request.getTitle(),
                request.getContent());

        return ResponseEntity.created(URI.create("/api/reivews/" + review.getId())).body(new ReviewResponse(review.getId()));
    }

    @PutMapping("/api/reviews/{reviewId}") //리뷰 수정
    public ReviewResponse update(@PathVariable("reviewId") Long reviewId,
                                 @RequestBody UpdateRequest request) {
        reviewService.updateReview(reviewId, request.getTitle(), request.getContent());


        return new ReviewResponse(reviewId);
    }

    @GetMapping("/api/reviews") //전체 리뷰 조회
    public Result reviews() {

        List<Review> reviews = reviewService.findReviews();
        List<Object> collect = reviews.stream()
                .map(r -> new ReviewList(r.getId(), r.getMember().getId(), r.getItem().getId(), r.getTitle(), r.getContent()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/reviews/{reviewId}") //리뷰 단건 조회
    public ReviewList review(@PathVariable("reviewId") Long reviewId) {
        Review review = reviewService.findReview(reviewId);
        ReviewList reviewList = new ReviewList(review.getId(),
                review.getMember().getId(),
                review.getItem().getId(),
                review.getTitle(),
                review.getContent());
        return reviewList;
    }

    @GetMapping("/api/reviews/member/{memberId}") //회원별 리뷰 조회
    public Result memberReview(@PathVariable("memberId") Long memberId) {
        List<Review> memberReview = reviewService.findMemberReview(memberId);
        List<MemberReviewList> collect = memberReview.stream()
                .map(m -> new MemberReviewList(m.getItem().getId(), m.getTitle(), m.getContent()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @DeleteMapping("/api/reviews/{reviewId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);

        Map<String, String> response = new HashMap<>();
        response.put("message","리뷰가 삭제되었습니다.");

        return ResponseEntity.ok(response);
    }
}
