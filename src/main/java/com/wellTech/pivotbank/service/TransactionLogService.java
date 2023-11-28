package com.wellTech.pivotbank.service;

import com.wellTech.pivotbank.dto.TransactionLogDTO;
import com.wellTech.pivotbank.entity.TransactionLog;
import com.wellTech.pivotbank.repository.TransactionLogRepo;

public interface TransactionLogService {

    void logTransaction(TransactionLogDTO transactionLogDTO);

}
