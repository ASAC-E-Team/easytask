package com.easytask.easytask.src.user.entity;

import com.easytask.easytask.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @Column(name = "userId", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 30)
    private String name;

    private String password;

    @OneToMany(mappedBy = "user")
    List<PossibleTask> possibleTaskList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<TaskAbility> taskAbilityList = new ArrayList<>();



}
