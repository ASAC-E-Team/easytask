package com.easytask.easytask.src.task;

import com.easytask.easytask.src.task.entity.RelatedAbility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RelatedAbilityRepository extends JpaRepository<RelatedAbility, Long> {
    Optional<RelatedAbility> findById(Long id);
}
