package com.wellTech.pivotbank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record UserDTO(     String firstName,
                           String lastName,
                           String otherName,
                           String gender,
                           String address,
                           String countryOfOrigin,
                           String email,
                           String phoneNumber,
                           String otherPhoneNumber) {


}
