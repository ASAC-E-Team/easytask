package com.easytask.easytask.src.review;

import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.src.review.dto.GetReviewRes;
import com.easytask.easytask.src.review.dto.PostPersonalAbilityReq;
import com.easytask.easytask.src.review.dto.PostRequestRatingDto;
import com.easytask.easytask.src.review.dto.PostRequestReviewDto;
import com.easytask.easytask.src.review.entity.PersonalAbility;
import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.review.repository.PersonalAbilityRepository;
import com.easytask.easytask.src.review.repository.RatingRepository;
import com.easytask.easytask.src.review.repository.ReviewRepository;
import com.easytask.easytask.src.task.RelatedAbilityRepository;
import com.easytask.easytask.src.task.TaskRepository;
import com.easytask.easytask.src.task.TaskUserMappingRepository;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TaskRepository taskRepository;
    private final TaskUserMappingRepository taskUserMappingRepository;
    private final PersonalAbilityRepository personalAbilityRepository;
    private final RelatedAbilityRepository relatedAbilityRepository;
    private final RatingRepository ratingRepository;

    public Long createReview(PostRequestReviewDto postRequestReviewDto) {
        System.out.println(postRequestReviewDto.getTask().getId());
        Task task = taskRepository.findById(postRequestReviewDto.getTask().getId())
                .orElseThrow();

        TaskUserMapping taskUserMapping = taskUserMappingRepository.findById(postRequestReviewDto.getTaskUserMapping().getId())
                .orElseThrow();

        postRequestReviewDto.setTask(task);
        postRequestReviewDto.setTaskUserMapping(taskUserMapping);

        Review review = postRequestReviewDto.toEntity();
        reviewRepository.save(review);

//        for (PostRequestRatingDto postRequestRatingDto : postRequestRatingDtoList) {
//            PersonalAbility personalAbility = personalAbilityRepository.findById(postRequestRatingDto.getPersonalAbility().getId())
//                    .orElseThrow();
//
//            RelatedAbility relatedAbility = relatedAbilityRepository.findById(postRequestRatingDto.getRelatedAbility().getId())
//                    .orElseThrow();
//
//            postRequestRatingDto.setPersonalAbility(personalAbility);
//            postRequestRatingDto.setRelatedAbility(relatedAbility);
//
//            Rating rating = postRequestRatingDto.toEntity();
//            review.addRatingList(rating);
//        }

        return review.getId();
    }

    public Review addRatingsOfReview(Long reviewId, List<PostRequestRatingDto> postRequestRatingDtoList) {
        Review review = reviewRepository.findOne(reviewId);

        for (PostRequestRatingDto postRequestRatingDto : postRequestRatingDtoList) {
            PersonalAbility personalAbility = personalAbilityRepository.findById(postRequestRatingDto.getPersonalAbility().getId())
                    .orElseThrow();

            RelatedAbility relatedAbility = relatedAbilityRepository.findById(postRequestRatingDto.getRelatedAbility().getId())
                    .orElseThrow();

            postRequestRatingDto.setPersonalAbility(personalAbility);
            postRequestRatingDto.setRelatedAbility(relatedAbility);
            postRequestRatingDto.setReview(review);

            Rating rating = postRequestRatingDto.toEntity();
            ratingRepository.save(rating);
            review.addRatingList(rating);
        }

        return review;
    }

    @Transactional(readOnly = true)
    public GetReviewRes getReview(Long reviewId) {
        Review review = reviewRepository.findOne(reviewId);
        return new GetReviewRes(review);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByUserId(Long userId) {
        List<Review> reviewList = reviewRepository.getReviewsByUserId(userId);
        return reviewList;
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByIrumiId(Long irumiId) {
        List<Review> reviewList = reviewRepository.getReviewsByIrumiId(irumiId);
        return reviewList;
    }

    public Long createPersonalAbility(PostPersonalAbilityReq postPersonalAbilityReq) {
        PersonalAbility personalAbility = postPersonalAbilityReq.toEntity(postPersonalAbilityReq);
        personalAbilityRepository.save(personalAbility);
        return personalAbility.getId();
    }

    public List<Double> getAverageRating(Long reviewId) {
        Review review = reviewRepository.findOne(reviewId);


        List<Rating> ratingList = review.getRatingList();

        List<Double> list = new ArrayList<>();

        double personalAbilityRatingSum = 0;
        double relatedAbilityRatingSum = 0;

        for (Rating rating : ratingList) {
            personalAbilityRatingSum += rating.getPersonalAbilityRating();
            relatedAbilityRatingSum += rating.getRelatedAbilityRating();
        }

        int ratingCount = ratingList.size();
        double personalAbilityRatingAvg = personalAbilityRatingSum / ratingCount;
        double relatedAbilityRatingAvg = relatedAbilityRatingSum / ratingCount;

        list.add(personalAbilityRatingAvg);
        list.add(relatedAbilityRatingAvg);

        return list;
    }
}
