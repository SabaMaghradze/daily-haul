package com.app.daily_haul.service.impl;

import com.app.daily_haul.dto.AddressDTO;
import com.app.daily_haul.dto.UserRequest;
import com.app.daily_haul.dto.UserResponse;
import com.app.daily_haul.model.Address;
import com.app.daily_haul.model.User;
import com.app.daily_haul.repository.UserRepository;
import com.app.daily_haul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserResponse getUserResponse(User user) {
        UserResponse userResponse = new UserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO(user.getAddress().getStreet(),
                    user.getAddress().getCity(),
                    user.getAddress().getState(),
                    user.getAddress().getCountry(),
                    user.getAddress().getZipcode());

            userResponse.setAddress(addressDTO);
        }

        return userResponse;
    }

    private User userReqToEnt(User user, UserRequest userRequest) {

        user.setFirstName(userRequest.getFirstName() != null ? userRequest.getFirstName() : user.getFirstName());
        user.setLastName(userRequest.getLastName() != null ? userRequest.getLastName() : user.getLastName());
        user.setEmail(userRequest.getEmail() != null ? userRequest.getEmail() : user.getEmail());
        user.setPhone(userRequest.getPhone() != null ? userRequest.getPhone() : user.getPhone());

        if (userRequest.getAddress() != null) {
            Address address = new Address(userRequest.getAddress().getStreet(),
                    userRequest.getAddress().getCity(),
                    userRequest.getAddress().getState(),
                    userRequest.getAddress().getCountry(),
                    userRequest.getAddress().getZipcode());

            user.setAddress(address);
        }

        return user;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::getUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        userReqToEnt(user, userRequest);
        userRepository.save(user);
    }


    public Optional<UserResponse> getUser(Long userId) {
        return userRepository.findById(userId).map(this::getUserResponse);
    }

    public boolean updateUser(Long userId, UserRequest userRequest) {

        return userRepository.findById(userId)
                .map(existingUser -> {
                    userReqToEnt(existingUser, userRequest);
                    userRepository.save(existingUser);
                    return true;
                })
                .orElse(false);
    }
}
