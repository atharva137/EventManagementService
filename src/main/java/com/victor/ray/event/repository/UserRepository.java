package com.victor.ray.event.repository;

import com.victor.ray.event.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByMobileNumber(String mobileNumber);
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserId(Long userId);
}
