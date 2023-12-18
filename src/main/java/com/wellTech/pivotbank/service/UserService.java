package com.wellTech.pivotbank.service;

import com.wellTech.pivotbank.dto.*;

public interface UserService {

    BankResponse createUserAccount(UserDTO userDTO);
    BankResponse login(LoginDTO loginDTO);
    BankResponse accountBalanceEnquiry(AccountEnqDTO accountEnqDTO);
    BankResponse deposit(DepositWithdrawaDTO depositWithdrawaDTO);
    BankResponse withdraw(DepositWithdrawaDTO depositWithdrawaDTO);
    BankResponse transfer(TransferDTO transferDTO);
}
