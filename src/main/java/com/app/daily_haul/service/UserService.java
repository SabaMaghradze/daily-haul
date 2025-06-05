package com.app.daily_haul.service;

import com.app.daily_haul.dto.UserRequest;
import com.app.daily_haul.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserResponse> getAllUsers();

    void addUser(UserRequest userRequest);

    Optional<UserResponse> getUser(Long userId);

    boolean updateUser(Long userId, UserRequest userRequest);
}
