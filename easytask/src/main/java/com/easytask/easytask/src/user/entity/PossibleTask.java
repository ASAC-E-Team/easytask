package com.easytask.easytask.src.user.entity;

import com.easytask.easytask.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "POSSIBLETASK")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PossibleTask extends BaseEntity {
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
    public PossibleTask(User user, String categoryBig, String categorySmall) {
        this.user = user;
        this.categoryBig = categoryBig;
        this.categorySmall = categorySmall;
    }
}
