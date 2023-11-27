package com.wellTech.pivotbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record EmailDetailsDTO( @NotNull String recipient,
                               @NotNull String messageBody,
                               @NotNull String subject,
                               @NotNull String attachment) {



}
