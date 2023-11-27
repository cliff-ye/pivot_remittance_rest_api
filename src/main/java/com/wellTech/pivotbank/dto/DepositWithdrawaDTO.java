package com.wellTech.pivotbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DepositWithdrawaDTO(@NotNull String accountNumber, @NotNull BigDecimal amount) {

}
