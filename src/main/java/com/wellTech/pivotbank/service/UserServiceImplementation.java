package com.wellTech.pivotbank.service;

import com.wellTech.pivotbank.dto.*;
import com.wellTech.pivotbank.entity.TransactionLog;
import com.wellTech.pivotbank.entity.User;
import com.wellTech.pivotbank.repository.UserRepo;
import com.wellTech.pivotbank.utils.AccNumbGenerator;
import com.wellTech.pivotbank.utils.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImplementation implements UserService{

    private UserRepo userRepo;
    private EmailService emailService;
    private TransactionLogService transactionLogService;
    private   TransactionLogDTO logDto;
    @Autowired
    public UserServiceImplementation(UserRepo userRepo,EmailService emailService,TransactionLogService transactionLogService){
        this.userRepo = userRepo;
        this.emailService=emailService;
        this.transactionLogService= transactionLogService;
    }
    @Override
    public BankResponse createUserAccount(UserDTO userDTO) {
        User newUser = User.builder()
                        .firstName(userDTO.firstName())
                        .lastName(userDTO.lastName())
                        .otherName(userDTO.otherName())
                        .gender(userDTO.gender())
                        .address(userDTO.address())
                        .countryOfOrigin(userDTO.countryOfOrigin())
                        .accountNumber(AccNumbGenerator.generateAccountNumber())
                        .accountBalance(BigDecimal.ZERO)
                        .email(userDTO.email())
                        .phoneNumber(userDTO.phoneNumber())
                        .otherPhoneNumber(userDTO.otherPhoneNumber())
                        .status("active")
                        .build();

        //TODO: Check if user has an account already
        if(userRepo.existsByEmail(userDTO.email())){
            return BankResponse.builder()
                    .responseMessage(CustomUtils.ACCOUNT_EXISTS_MESSAGE)
                    .responseCode(CustomUtils.ACCOUNT_EXISTS_CODE)
                    .accountDetailsDTO(null)
                    .build();
        }

        User savedNewUser = userRepo.save(newUser);

        String message = "Dear "+savedNewUser.getFirstName()+",\nYour Pivot Bank account was created successfully.\nYour" +
                "Account Number : "+savedNewUser.getAccountNumber()+"\nYour Account Balance : "+savedNewUser.getAccountBalance();

        EmailDetailsDTO emailDetailsDTO = EmailDetailsDTO.builder()
                .recipient(savedNewUser.getEmail())
                .messageBody(message)
                .subject("Pivot Bank Ghana")
                .build();

        emailService.sendEmail(emailDetailsDTO);

        return BankResponse.builder()
                .responseMessage(CustomUtils.ACCOUNT_CREATED_MESSAGE)
                .responseCode(CustomUtils.ACCOUNT_CREATED_CODE)
                .accountDetailsDTO(AccountDetailsDTO.builder().accountName(savedNewUser.getFirstName()+" "+savedNewUser.getLastName()+" "+savedNewUser.getOtherName())
                                                              .accountBalance(savedNewUser.getAccountBalance())
                                                              .accountNumber(savedNewUser.getAccountNumber())
                                                              .build())
                .build();

    }

    @Override
    public BankResponse accountBalanceEnquiry(AccountEnqDTO accountEnqDTO) {

      Boolean isAcctExist = userRepo.existsByAccountNumber(accountEnqDTO.accountNumber());

        if(!isAcctExist){
            return BankResponse.builder()
                    .responseMessage(CustomUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .responseCode(CustomUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .accountDetailsDTO(null)
                    .build();
        }

        User user = userRepo.findByAccountNumber(accountEnqDTO.accountNumber());

        return BankResponse.builder()
                .responseCode(CustomUtils.ACCOUNT_EXISTS_CODE)
                .responseMessage(CustomUtils.ACCOUNT_EXISTS_MESSAGE)
                .accountDetailsDTO(AccountDetailsDTO.builder().accountNumber(user.getAccountNumber())
                .accountName(user.getFirstName()+" "+user.getLastName())
                .accountBalance(user.getAccountBalance())
                .build())
                .build();
    }

    @Override
    public BankResponse deposit(DepositWithdrawaDTO depositWithdrawaDTO) {
        Boolean isAcctExist = userRepo.existsByAccountNumber(depositWithdrawaDTO.accountNumber());

        if(!isAcctExist){
            return BankResponse.builder()
                    .responseMessage(CustomUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .responseCode(CustomUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .accountDetailsDTO(null)
                    .build();
        }

        User recipientAcc = userRepo.findByAccountNumber(depositWithdrawaDTO.accountNumber());
        recipientAcc.setAccountBalance(recipientAcc.getAccountBalance().add(depositWithdrawaDTO.amount()));

        userRepo.save(recipientAcc);

        //log transaction
       logDto = TransactionLogDTO.builder()
                .transactionType("deposit")
                .amount(depositWithdrawaDTO.amount())
                .accountNumber(recipientAcc.getAccountNumber())
                .status("success")
                .build();

        transactionLogService.logTransaction(logDto);

        return BankResponse.builder()
                .responseMessage(CustomUtils.DEPOSIT_SUCCESS_MESSAGE)
                .responseCode(CustomUtils.DEPOSIT_SUCCESS_CODE)
                .accountDetailsDTO(AccountDetailsDTO.builder()
                        .accountBalance(recipientAcc.getAccountBalance())
                        .accountName(recipientAcc.getFirstName()+" "+recipientAcc.getLastName())
                        .accountNumber(recipientAcc.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse withdraw(DepositWithdrawaDTO depositWithdrawaDTO) {
        Boolean isAcctExist = userRepo.existsByAccountNumber(depositWithdrawaDTO.accountNumber());

        if(!isAcctExist){
            return BankResponse.builder()
                    .responseMessage(CustomUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .responseCode(CustomUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .accountDetailsDTO(null)
                    .build();
        }

        User userToDebit = userRepo.findByAccountNumber(depositWithdrawaDTO.accountNumber());

        Double currentBal = Double.parseDouble(userToDebit.getAccountBalance().toString());
        Double amountToDebit = Double.parseDouble(depositWithdrawaDTO.amount().toString());

        if(currentBal < amountToDebit){
            return BankResponse.builder()
                    .responseMessage(CustomUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .responseCode(CustomUtils.INSUFFICIENT_FUNDS_CODE)
                    .accountDetailsDTO(null)
                    .build();
        }
        userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(depositWithdrawaDTO.amount()));

        userRepo.save(userToDebit);

                //log transaction
                logDto = TransactionLogDTO.builder()
                .transactionType("withdrawal")
                .amount(depositWithdrawaDTO.amount())
                .accountNumber(userToDebit.getAccountNumber())
                .status("success")
                .build();

        transactionLogService.logTransaction(logDto);

        return BankResponse.builder()
                .responseMessage(CustomUtils.DEBIT_SUCCESS_MESSAGE)
                .responseCode(CustomUtils.DEBIT_SUCCESS_CODE)
                .accountDetailsDTO(AccountDetailsDTO.builder()
                        .accountBalance(userToDebit.getAccountBalance())
                        .accountName(userToDebit.getFirstName()+" "+userToDebit.getLastName())
                        .accountNumber(userToDebit.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse transfer(TransferDTO transferDTO) {

        Boolean senderExist = userRepo.existsByAccountNumber(transferDTO.senderAccNumber());
        Boolean recipientExist = userRepo.existsByAccountNumber(transferDTO.recipientAccNum());

        if(!senderExist || !recipientExist){
            return BankResponse.builder()
                    .responseMessage(CustomUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .responseCode(CustomUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .accountDetailsDTO(null)
                    .build();
        }

        User sender = userRepo.findByAccountNumber(transferDTO.senderAccNumber());

        Double senderCurrentBal = Double.parseDouble(sender.getAccountBalance().toString());
        Double amoutToTransfer = Double.parseDouble(transferDTO.amount().toString());

        if(senderCurrentBal<amoutToTransfer){
            return BankResponse.builder()
                    .responseMessage(CustomUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .responseCode(CustomUtils.INSUFFICIENT_FUNDS_CODE)
                    .accountDetailsDTO(null)
                    .build();
        }

        if(amoutToTransfer <= 0){
            return BankResponse.builder()
                    .responseMessage(CustomUtils.INVALID_AMOUNT_MESSAGE)
                    .responseCode(CustomUtils.INVALID_AMOUNT_CODE)
                    .accountDetailsDTO(null)
                    .build();
        }

        sender.setAccountBalance(sender.getAccountBalance().subtract(transferDTO.amount()));
        userRepo.save(sender);
        String message1 = "Dear "+sender.getFirstName()+",\nYou have successfully sent GHS "+ transferDTO.amount()+" to "+transferDTO.recipientAccNum()+
                "\nYour Current Balance GHS "+sender.getAccountBalance();

        EmailDetailsDTO senderAlert = EmailDetailsDTO.builder()
                .recipient(sender.getEmail())
                .messageBody(message1)
                .subject("Pivot Bank Ghana")
                .build();

        emailService.sendEmail(senderAlert);

        User recipient = userRepo.findByAccountNumber(transferDTO.recipientAccNum());
        recipient.setAccountBalance(recipient.getAccountBalance().add(transferDTO.amount()));
        userRepo.save(recipient);

        //log transaction
        logDto = TransactionLogDTO.builder()
                .transactionType("Transfer")
                .amount(transferDTO.amount())
                .accountNumber(recipient.getAccountNumber())
                .status("success")
                .build();

        transactionLogService.logTransaction(logDto);

        String message2 = "Dear "+recipient.getFirstName()+",\nYou have received GHS "+ transferDTO.amount()+" from "+transferDTO.senderAccNumber()+
                "\nYour Current Balance GHS "+recipient.getAccountBalance();

        EmailDetailsDTO recipientAlert = EmailDetailsDTO.builder()
                .recipient(recipient.getEmail())
                .messageBody(message2)
                .subject("Pivot Bank Ghana")
                .build();

        emailService.sendEmail(recipientAlert);

        return BankResponse.builder()
                .responseMessage(CustomUtils.TRANSFER_SUCCESS_MESSAGE)
                .responseCode(CustomUtils.TRANSFER_SUCCESS_CODE)
                .accountDetailsDTO(AccountDetailsDTO.builder()
                        .accountBalance(sender.getAccountBalance())
                        .accountName(sender.getFirstName()+" "+sender.getLastName())
                        .accountNumber(sender.getAccountNumber())
                        .build())
                .build();
    }


}
