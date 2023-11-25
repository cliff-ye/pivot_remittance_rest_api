package com.wellTech.pivotbank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
public record AccountDetailsDTO(  String accountNumber,
                                  String accountName,
                                  BigDecimal accountBalance) {



}
