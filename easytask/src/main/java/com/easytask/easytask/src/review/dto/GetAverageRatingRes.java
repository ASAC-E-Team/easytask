package com.easytask.easytask.src.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAverageRatingRes {
    private String personalAbility = "개인 역량";
    private double personalAverage;
    private List<PersonalAbilityList> getPersonalAbilitylists = new ArrayList<>();
    private String relatedAbilitly = "전문 역량";
    private double relatedAverage;
    private List<RelatedAbilityList> getRelatedAbilitylists = new ArrayList<>();


}
