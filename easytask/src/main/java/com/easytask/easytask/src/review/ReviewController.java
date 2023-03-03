package com.easytask.easytask.src.review;

import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.src.review.dto.GetReviewRes;
import com.easytask.easytask.src.review.dto.PostRequestRatingDto;
import com.easytask.easytask.src.review.dto.PostRequestReviewDto;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    @ResponseBody
    @PostMapping("")
    public BaseResponse<Review> createReview(@RequestBody PostRequestReviewDto postRequestReviewDto,
                                             @RequestBody List<PostRequestRatingDto> postRequestRatingDtoList) {
        Long reviewId = reviewService.createReview(postRequestReviewDto, postRequestRatingDtoList);
        Review review = reviewRepository.findOne(reviewId);

        return new BaseResponse<>(review);
    }

    @GetMapping("/{reviewId}")
    public BaseResponse<GetReviewRes> getReview(@PathVariable("reviewId") Long reviewId) {
        GetReviewRes review = reviewService.getReview(reviewId);
        return new BaseResponse<>(review);
    }


}
