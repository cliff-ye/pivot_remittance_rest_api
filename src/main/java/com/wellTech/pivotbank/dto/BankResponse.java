package com.wellTech.pivotbank.dto;

import com.wellTech.pivotbank.dto.AccountDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
public record BankResponse( String responseCode,
         String responseMessage,
         AccountDetailsDTO accountDetailsDTO) {

}
