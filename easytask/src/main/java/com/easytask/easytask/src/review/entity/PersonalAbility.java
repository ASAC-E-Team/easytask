package com.easytask.easytask.src.review.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String personalAbility;

    public PersonalAbility(String personalAbility) {
        this.personalAbility = personalAbility;
    }
}
