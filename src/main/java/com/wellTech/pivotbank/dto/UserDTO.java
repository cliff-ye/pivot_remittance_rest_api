package com.wellTech.pivotbank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record UserDTO(   @NotNull @Size(min = 2) String firstName,
                          @NotNull @Size(min = 2) String lastName,
                           String otherName,
                           @NotNull String gender,
                           @NotNull String address,
                           @NotNull String countryOfOrigin,
                           @NotNull @Email String email,
                           @NotNull String phoneNumber,
                           String otherPhoneNumber) {


}
