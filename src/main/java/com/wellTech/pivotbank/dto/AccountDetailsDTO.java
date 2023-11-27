package com.wellTech.pivotbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder

public record AccountDetailsDTO(  @NotNull String accountNumber,
                                  @NotNull String accountName,
                                  @NotNull BigDecimal accountBalance) {



}
