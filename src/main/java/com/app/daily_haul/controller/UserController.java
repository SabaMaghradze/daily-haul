package com.app.daily_haul.controller;

import com.app.daily_haul.dto.UserRequest;
import com.app.daily_haul.dto.UserResponse;
import com.app.daily_haul.model.User;
import com.app.daily_haul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return ResponseEntity.ok("User added successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {

        boolean updated = userService.updateUser(id, userRequest);

        if (updated) {
            return ResponseEntity.ok("User updated successfully");
        }

        return ResponseEntity.notFound().build();
    }
}
