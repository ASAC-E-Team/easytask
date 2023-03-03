package com.easytask.easytask.src.review.entity;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Rating extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ratingId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relatedAbilityId")
    private RelatedAbility relatedAbility;
    @ManyToOne
    @JoinColumn(name = "personalAbilityId")
    private PersonalAbility personalAbility;
    private int relatedAbilityRating;
    private int personalAbilityRating;

    @Builder
    public Rating(RelatedAbility relatedAbility, PersonalAbility personalAbility, int relatedAbilityRating, int personalAbilityRating) {
        this.relatedAbility = relatedAbility;
        this.personalAbility = personalAbility;
        this.relatedAbilityRating = relatedAbilityRating;
        this.personalAbilityRating = personalAbilityRating;
    }


}
