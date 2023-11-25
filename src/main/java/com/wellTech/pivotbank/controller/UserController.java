package com.wellTech.pivotbank.controller;

import com.wellTech.pivotbank.dto.*;
import com.wellTech.pivotbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping()
    public BankResponse createAccount(@RequestBody UserDTO userDTO){
        return userService.createUserAccount(userDTO);
    }

    @GetMapping("/balanceEnq")
    public BankResponse accBalEnquiry(@RequestBody AccountEnqDTO accountEnqDTO){
        return userService.accountBalanceEnquiry(accountEnqDTO);
    }

    @PostMapping("/deposit")
    public BankResponse creditAcc(@RequestBody DepositWithdrawaDTO depositWithdrawaDTO){
        return userService.deposit(depositWithdrawaDTO);
    }

    @PostMapping("/withdraw")
    public BankResponse debitAcc(@RequestBody DepositWithdrawaDTO depositWithdrawaDTO){
        return userService.withdraw(depositWithdrawaDTO);
    }

    @PostMapping("/transfer")
    public BankResponse makeTransfer(@RequestBody TransferDTO transferDTO){
        return userService.transfer(transferDTO);
    }
}
