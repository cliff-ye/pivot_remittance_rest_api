package com.wellTech.pivotbank.service;

import com.wellTech.pivotbank.dto.TransactionLogDTO;
import com.wellTech.pivotbank.entity.TransactionLog;
import com.wellTech.pivotbank.repository.TransactionLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactLogSeviceImpl implements TransactionLogService{

    @Autowired
    TransactionLogRepo transactionLogRepo;

    @Override
    public void logTransaction(TransactionLogDTO transactionLogDTO) {
        TransactionLog log = TransactionLog.builder()
                .transactionType(transactionLogDTO.transactionType())
                .amount(transactionLogDTO.amount())
                .accountNumber(transactionLogDTO.accountNumber())
                .status(transactionLogDTO.status())
                .build();

        transactionLogRepo.save(log);

        System.out.println("logged transaction");
    }
}
