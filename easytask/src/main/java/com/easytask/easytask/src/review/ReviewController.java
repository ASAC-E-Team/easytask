package com.easytask.easytask.src.review;

import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.src.review.dto.*;
import com.easytask.easytask.src.review.entity.PersonalAbility;
import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.review.repository.PersonalAbilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final PersonalAbilityRepository personalAbilityRepository;

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostReviewRes> createReview(@RequestBody PostReviewReq postReviewReq) {
        PostReviewRes postReviewRes = reviewService.createReview(postReviewReq);
        return new BaseResponse<>(postReviewRes);
    }

    @ResponseBody
    @GetMapping("/{reviewId}")
    public BaseResponse<GetReviewRes> getReview(@PathVariable("reviewId") Long reviewId) {
        GetReviewRes review = reviewService.getReview(reviewId);
        return new BaseResponse<>(review);
    }

    @ResponseBody
    @GetMapping("/lists/users/{userId}")
    public BaseResponse<List<PostReviewRes>> getReviewsByUserId(@PathVariable("userId") Long userId) {
        List<Review> reviewList = reviewService.getReviewsByUserId(userId);
        List<PostReviewRes> list = new ArrayList<>();

        for (Review review : reviewList) {
            PostReviewRes postReviewRes = new PostReviewRes(review);

            for (Rating rating: review.getRatingList()) {
                PostRatingRes postRatingRes = new PostRatingRes(rating);
                postReviewRes.addPostRatingRes(postRatingRes);
            }
            list.add(postReviewRes);
        }

        return new BaseResponse<>(list);
    }

    @ResponseBody
    @GetMapping("/lists/irumies/{irumiId}")
    public BaseResponse<List<PostReviewRes>> getReviewsByIrumiId(@PathVariable("irumiId") Long irumiId) {
        List<Review> reviewList = reviewService.getReviewsByIrumiId(irumiId);
        List<PostReviewRes> list = new ArrayList<>();

        for (Review review : reviewList) {
            PostReviewRes postReviewRes = new PostReviewRes(review);

            for (Rating rating: review.getRatingList()) {
                PostRatingRes postRatingRes = new PostRatingRes(rating);
                postReviewRes.addPostRatingRes(postRatingRes);
            }
            list.add(postReviewRes);
        }

        return new BaseResponse<>(list);
    }

    @ResponseBody
    @PostMapping("/add/rating/{reviewId}")
    public BaseResponse<PostReviewRes> addRatingsOfReview(@PathVariable("reviewId") Long reviewId,
                                                         @RequestBody List<PostRatingReq> postRatingReqList) {
        PostReviewRes postReviewRes = reviewService.addRatingsOfReview(reviewId, postRatingReqList);
        return new BaseResponse<>(postReviewRes);
    }

    @ResponseBody
    @PostMapping("/personal")
    public BaseResponse<Optional<PersonalAbility>> createPersonalAbility(@RequestBody PostPersonalAbilityReq postPersonalAbilityReq) {
       Long personalAbilityId = reviewService.createPersonalAbility(postPersonalAbilityReq);
       Optional<PersonalAbility> personalAbility = personalAbilityRepository.findById(personalAbilityId);
       return new BaseResponse<>(personalAbility);
    }

    @GetMapping("/{reviewId}/average")
    public BaseResponse<GetAverageRatingRes> getAverageRating(@PathVariable Long reviewId) {
        GetAverageRatingRes getAverageRatingRes = reviewService.getAverageRating(reviewId);
        return new BaseResponse<>(getAverageRatingRes);
    }


}
