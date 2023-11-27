package com.wellTech.pivotbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AccountEnqDTO(@NotNull String accountNumber) {
}
