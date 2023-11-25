package com.wellTech.pivotbank.service;

import com.wellTech.pivotbank.dto.EmailDetailsDTO;

public interface EmailService {

    void sendEmail(EmailDetailsDTO emailDetailsDTO);
}
