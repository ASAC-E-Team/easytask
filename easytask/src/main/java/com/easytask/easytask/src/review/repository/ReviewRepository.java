package com.easytask.easytask.src.review.repository;

import com.easytask.easytask.common.BaseEntity.State;
import com.easytask.easytask.common.exception.BaseException;

import com.easytask.easytask.common.response.BaseResponseStatus;
import static com.easytask.easytask.common.response.BaseResponseStatus.*;
import com.easytask.easytask.src.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager entityManger;
    public void save(Review review) {
        entityManger.persist(review);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return entityManger.createQuery("select r from Review r join r.task t join t.customer u where u.userId = :userId", Review.class)
                .setParameter("userId", userId).getResultList();
    }

    public Review findOne(Long reviewId) {
        return entityManger.find(Review.class, reviewId);
    }

}
