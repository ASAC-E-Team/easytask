package com.easytask.easytask.src.review.dto;

import com.easytask.easytask.src.review.entity.PersonalAbility;
import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Collectors;

@Getter
@Setter
public class PostRequestRatingDto {

    private Review review;
    private PersonalAbility personalAbility;
    private RelatedAbility relatedAbility;
    private int relatedAbilityRating;
    private int personalAbilityRating;

    public Rating toEntity() {
        return Rating.builder()
                .review(review)
                .personalAbility(personalAbility)
                .relatedAbility(relatedAbility)
                .relatedAbilityRating(relatedAbilityRating)
                .personalAbilityRating(personalAbilityRating)
                .build();
    }

}
