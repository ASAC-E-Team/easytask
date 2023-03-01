package com.easytask.easytask.src.task.entity;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.user.entity.User;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User customer;

    private String taskName;

    private String details;

    private String categoryBig;

    private String categorySmall;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TaskUserMapping> IrumiList = new ArrayList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RelatedAbility> relatedAbilityList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus = MatchingStatus.NOT_MATCHED;

    public enum MatchingStatus {
        NOT_MATCHED, NOT_STARTED, DOING, DONE
    }

    public Task(User user, String taskName, String categoryBig, String categorySmall) {}
}
