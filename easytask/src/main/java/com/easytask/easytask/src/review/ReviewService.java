package com.easytask.easytask.src.review;

import com.easytask.easytask.src.review.dto.GetReviewRes;
import com.easytask.easytask.src.review.dto.PostRequestRatingDto;
import com.easytask.easytask.src.review.dto.PostRequestReviewDto;
import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Long createReview(PostRequestReviewDto postRequestReviewDto,
                             List<PostRequestRatingDto> postRequestRatingDtoList) {
        Review review = postRequestReviewDto.toEntity();
        reviewRepository.save(review);

        for (PostRequestRatingDto postRequestRatingDto : postRequestRatingDtoList) {
            Rating rating = postRequestRatingDto.toEntity();
            review.addRatingList(rating);
        }

        return review.getId();
    }

    public GetReviewRes getReview(Long reviewId) {
        Review review = reviewRepository.findOne(reviewId);
        return new GetReviewRes(review);
    }

}
