package com.easytask.easytask.src.review.dto;

import com.easytask.easytask.src.review.entity.PersonalAbility;
import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import lombok.Builder;

import java.util.stream.Collectors;

public class PostRequestRatingDto {

    private PersonalAbility personalAbility;
    private RelatedAbility relatedAbility;
    private int relatedAbilityRating;
    private int personalAbilityRating;

    public Rating toEntity() {

        return Rating.builder()
                .personalAbility(this.personalAbility)
                .relatedAbilityRating(relatedAbilityRating)
                .relatedAbilityRating(this.relatedAbilityRating)
                .personalAbilityRating(this.personalAbilityRating)
                .build();
    }
}
