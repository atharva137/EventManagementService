package com.victor.ray.event.service.impl;

import com.victor.ray.event.beans.UserRequest;
import com.victor.ray.event.beans.UserResponse;
import com.victor.ray.event.entity.UserEntity;
import com.victor.ray.event.exception.InvalidInputException;
import com.victor.ray.event.repository.UserRepository;
import com.victor.ray.event.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        userRepository.findByMobileNumber(userRequest.getMobileNumber())
                .ifPresent(user -> {
                    throw new InvalidInputException("User already exists for mobile number: " + userRequest.getMobileNumber());
                });

        UserEntity userEntity = mapUserEntity(userRequest, new UserEntity());
        UserEntity savedUser = userRepository.save(userEntity);

        return buildUserResponse(savedUser);
    }

    private UserEntity mapUserEntity(UserRequest userRequest, UserEntity userEntity) {
        userEntity.setMobileNumber(userRequest.getMobileNumber());
        userEntity.setActive(true);
        userEntity.setEmail(userRequest.getEmailId());
        userEntity.setRole(userRequest.getRole());
        userEntity.setProfileComplete(userRequest.getProfileComplete());
        return userEntity;
    }

    private UserResponse buildUserResponse(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userEntity.getUserId());
        userResponse.setMobileNumber(userEntity.getMobileNumber());
        return userResponse;
    }


}
