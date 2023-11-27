package com.wellTech.pivotbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferDTO(@NotNull String senderAccNumber,
                          @NotNull String recipientAccNum,
                          @NotNull BigDecimal amount) {
}
