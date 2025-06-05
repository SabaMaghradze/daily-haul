package com.app.daily_haul.dto;

import com.app.daily_haul.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private UserRole role = UserRole.CUSTOMER;

    private AddressDTO address;

    public UserResponse(Long id, String firstName, String lastName, String email, String phone, UserRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
}
