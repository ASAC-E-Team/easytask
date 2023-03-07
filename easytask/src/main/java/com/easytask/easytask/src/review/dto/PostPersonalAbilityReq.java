package com.easytask.easytask.src.review.dto;

import com.easytask.easytask.src.review.entity.PersonalAbility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPersonalAbilityReq {

    private String personalAbility;

    public PersonalAbility toEntity(PostPersonalAbilityReq postPersonalAbilityReq) {
        PersonalAbility personalAbility = new PersonalAbility(postPersonalAbilityReq.getPersonalAbility());
        return personalAbility;
    }
}
