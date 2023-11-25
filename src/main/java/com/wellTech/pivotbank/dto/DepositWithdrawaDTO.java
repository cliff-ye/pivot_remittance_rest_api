package com.wellTech.pivotbank.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DepositWithdrawaDTO(String accountNumber, BigDecimal amount) {

}
