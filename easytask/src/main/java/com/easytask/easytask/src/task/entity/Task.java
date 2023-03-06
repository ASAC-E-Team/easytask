package com.easytask.easytask.src.task.entity;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    @JsonIgnore
    private User customer;

    private String taskName;

    private String details;

    private String categoryBig;

    private String categorySmall;

//    private Integer numberOfIrumi;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TaskUserMapping> IrumiList = new ArrayList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RelatedAbility> relatedAbilityList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus = MatchingStatus.NOT_MATCHED;

    public enum MatchingStatus {
        NOT_MATCHED, NOT_STARTED, DOING, DONE
    }

    @Builder
    public Task(User user, String taskName, String details, String categoryBig, String categorySmall) {
        customer = user;
        this.taskName = taskName;
        this.details = details;
        this.categoryBig = categoryBig;
        this.categorySmall = categorySmall;
    }

    public void addIrumiList(TaskUserMapping taskUserMapping) {
        IrumiList.add(taskUserMapping);
    }

    public void addRelatedAbilityList(RelatedAbility relatedAbility) {
        relatedAbilityList.add(relatedAbility);
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }
}
