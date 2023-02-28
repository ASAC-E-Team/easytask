package com.easytask.easytask.src.task.repository;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelatedAbilityRepository extends JpaRepository<RelatedAbility, Long> {
    Optional<RelatedAbility> findByIdAndState(Long relatedAbilityId, BaseEntity.State active);
}
