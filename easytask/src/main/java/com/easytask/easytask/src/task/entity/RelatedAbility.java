package com.easytask.easytask.src.task.entity;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.user.entity.User;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RelatedAbility extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId")
    private Task task;

    private String categoryBig;

    private String categorySmall;
}
