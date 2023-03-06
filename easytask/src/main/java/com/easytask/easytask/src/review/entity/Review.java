package com.easytask.easytask.src.review.entity;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "taskId")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "taskUserMappingId")
    private TaskUserMapping taskUserMapping;

    @OneToMany(mappedBy = "review")
    private List<Rating> ratingList = new ArrayList<>();
    private String context;
    @Enumerated(EnumType.STRING)
    private Recommend recommend;

    @Builder
    public Review(Task task, TaskUserMapping taskUserMapping, String context, Recommend recommend) {
        this.task = task;
        this.taskUserMapping = taskUserMapping;
        this.context = context;
        this.recommend = recommend;
    }

    public enum Recommend {
        RECOMMEND, NOTRECOMMEND
    }


    public void addRatingList(Rating rating) {
        ratingList.add(rating);
    }


}