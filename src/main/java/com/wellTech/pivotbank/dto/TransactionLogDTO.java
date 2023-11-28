package com.wellTech.pivotbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionLogDTO( @NotNull String transactionType,
        @NotNull
         BigDecimal amount,
        @NotNull
         String accountNumber,
        @NotNull
         String status) {
}
