package com.victor.ray.event.service;

import com.victor.ray.event.beans.UserRequest;
import com.victor.ray.event.beans.UserResponse;
import com.victor.ray.event.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    public UserResponse createUser(UserRequest userRequest);

}
