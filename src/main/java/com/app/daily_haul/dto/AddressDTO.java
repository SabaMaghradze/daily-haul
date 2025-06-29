package com.app.daily_haul.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String street;

    private String city;

    private String state;

    private String country;

    private String zipcode;
}
