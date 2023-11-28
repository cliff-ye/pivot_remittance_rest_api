package com.wellTech.pivotbank.controller;

import com.itextpdf.text.DocumentException;
import com.wellTech.pivotbank.entity.TransactionLog;
import com.wellTech.pivotbank.service.BankStatement;
import jakarta.servlet.http.PushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/transactionStatement")
public class TransactionLogsController {

    private BankStatement bankStatement;

    @Autowired
    public TransactionLogsController(BankStatement bankStatement){
        this.bankStatement = bankStatement;
    }
    @GetMapping
    public List<TransactionLog> getStatements(@RequestParam String accountNumber,@RequestParam String month) throws DocumentException, FileNotFoundException {
       return bankStatement.getAllTransactionLog(accountNumber,month);
    }
}
