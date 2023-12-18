package com.wellTech.pivotbank.controller;

import com.wellTech.pivotbank.dto.*;
import com.wellTech.pivotbank.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api/users")

/* customize api docs - user controller */
@Tag(name="User Account REST API")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /* describe end point operation */
    @Operation(
            summary = "create user account",
            description = "creates a new user with a generated account number. Customer must not have an account" +
                    " already"
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "account created successfully")})

    @PostMapping()
    public BankResponse createAccount(@Valid @RequestBody UserDTO userDTO){
        return userService.createUserAccount(userDTO);
    }

    @PostMapping("/login")
    public BankResponse login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    @Operation(
            summary = "Gets Account Details",
            description = "User must exist"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "success"),
            @ApiResponse(responseCode = "404", description = "account not found")})
    @GetMapping("/balanceEnq")
    public BankResponse accBalEnquiry(@RequestBody AccountEnqDTO accountEnqDTO){
        return userService.accountBalanceEnquiry(accountEnqDTO);
    }

    @Operation(
            summary = "Deposit",
            description = "Account must exist"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "success"),
            @ApiResponse(responseCode = "404", description = "account not found")})
    @PostMapping("/deposit")
    public BankResponse creditAcc(@RequestBody DepositWithdrawaDTO depositWithdrawaDTO){
        return userService.deposit(depositWithdrawaDTO);
    }

    @Operation(
            summary = "Withdrawal",
            description = "Account must exist"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "success"),
            @ApiResponse(responseCode = "404", description = "account not found")})
    @PostMapping("/withdraw")
    public BankResponse debitAcc(@RequestBody DepositWithdrawaDTO depositWithdrawaDTO){
        return userService.withdraw(depositWithdrawaDTO);
    }

    @Operation(
            summary = "Transfer",
            description = "sender and recipient account must exist"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "success"),
            @ApiResponse(responseCode = "404", description = "account not found")})
    @PostMapping("/transfer")
    public BankResponse makeTransfer(@RequestBody TransferDTO transferDTO){
        return userService.transfer(transferDTO);
    }
}
