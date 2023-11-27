package com.wellTech.pivotbank.dto;

import com.wellTech.pivotbank.dto.AccountDetailsDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
public record BankResponse(@NotNull String responseCode,
                           @NotNull String responseMessage,
                           @NotNull AccountDetailsDTO accountDetailsDTO) {

}
