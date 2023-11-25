package com.wellTech.pivotbank.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferDTO(String senderAccNumber,
                          String recipientAccNum,
                          BigDecimal amount) {
}
