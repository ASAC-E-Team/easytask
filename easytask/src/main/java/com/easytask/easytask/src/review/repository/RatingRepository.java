package com.easytask.easytask.src.review.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class RatingRepository {

    private final EntityManager entityManager;


}
