package com.app.daily_haul.service.impl;

import com.app.daily_haul.dto.UserRequest;
import com.app.daily_haul.dto.UserResponse;
import com.app.daily_haul.model.User;
import com.app.daily_haul.repository.UserRepository;
import com.app.daily_haul.service.UserService;
import com.app.daily_haul.utils.Mappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(Mappers::getUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        Mappers.userReqToEnt(user, userRequest);
        userRepository.save(user);
    }

    public Optional<UserResponse> getUser(Long userId) {
        return userRepository.findById(userId).map(Mappers::getUserResponse);
    }

    public boolean updateUser(Long userId, UserRequest userRequest) {

        return userRepository.findById(userId)
                .map(existingUser -> {
                    Mappers.userReqToEnt(existingUser, userRequest);
                    userRepository.save(existingUser);
                    return true;
                })
                .orElse(false);
    }
}
