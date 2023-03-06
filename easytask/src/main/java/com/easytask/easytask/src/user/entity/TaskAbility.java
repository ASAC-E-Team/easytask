package com.easytask.easytask.src.user.entity;

import com.easytask.easytask.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
//@Table(name = "TASKABILITY")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskAbility extends BaseEntity {
    @Id
    @Column(name = "taskAbilityId", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String categoryBig;

    private String categorySmall;

    public TaskAbility(User user, String categoryBig, String categorySmall) {
        this.user = user;
        this.categoryBig = categoryBig;
        this.categorySmall = categorySmall;
    }

}
