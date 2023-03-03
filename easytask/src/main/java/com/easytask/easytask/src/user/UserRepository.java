package com.easytask.easytask.src.user;

import com.easytask.easytask.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public User findOne(Long userId) {
        return entityManager.find(User.class, userId);
    }
}
