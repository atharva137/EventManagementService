package com.victor.ray.event.repository;

import com.victor.ray.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity,Long> {

    // Custom query methods

    List<EventEntity> findByUserId(Long userId);

    List<EventEntity> findByStatus(String status);
}
