package com.easytask.easytask.src.user.entity;

import com.easytask.easytask.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TASKABILITY")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskAbility{
    @Id
    @Column(name = "professionalSkillId", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String categoryBig;

    private String categorySmall;

    @Builder
    public TaskAbility(User user, String categoryBig, String categorySmall) {
        this.user = user;
        this.categoryBig = categoryBig;
        this.categorySmall = categorySmall;
    }
    public void updateSkill(String categoryBig, String categorySmall){
        this.categoryBig=categoryBig;
        this.categorySmall=categorySmall;
    }
}
