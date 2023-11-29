package com.wellTech.pivotbank.service;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wellTech.pivotbank.dto.EmailDetailsDTO;
import com.wellTech.pivotbank.entity.TransactionLog;
import com.wellTech.pivotbank.entity.User;
import com.wellTech.pivotbank.repository.TransactionLogRepo;
import com.wellTech.pivotbank.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j //for logging
public class BankStatement {

    @Autowired
    private TransactionLogRepo transactionLogRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailService emailService;

    public static final String FILE = "D:\\Clifford\\Documents\\springframework\\pivot-bank\\statements.pdf";

    public List<TransactionLog> getAllTransactionLog(String accountNumber,String month) throws FileNotFoundException, DocumentException {
//        LocalDateTime beginDate = LocalDateTime.parse(month, DateTimeFormatter.ISO_DATE);

        Predicate<?super TransactionLog> condition1 = transaction -> transaction.getAccountNumber().equals(accountNumber);
        Predicate<?super TransactionLog> condition2 = transaction -> transaction.getSentAt().getMonth().name().equalsIgnoreCase(month);

        List<TransactionLog> logList = transactionLogRepo.findAll()
                .stream()
                .filter(condition1)
                .filter(condition2).toList();

        User user = userRepo.findByAccountNumber(accountNumber);

        Rectangle fileSize = new Rectangle(PageSize.A4);
        Document document = new Document(fileSize);

        log.info("set size of document");

        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        PdfPTable infoTable = new PdfPTable(1);

        PdfPCell bankName = new PdfPCell(new Phrase("Pivot Remittance Ghana"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(new BaseColor(70,90,242));
        bankName.setPadding(20f);


        PdfPCell addressSection = new PdfPCell(new Phrase("Accra, Ghana"));
        addressSection.setBorder(0);

        infoTable.addCell(bankName);
        //infoTable.addCell(addressSection);

        PdfPTable statementDetails = new PdfPTable(2);

        PdfPCell statementMonth = new PdfPCell(new Phrase("Month : "+month.toUpperCase()));
        statementMonth.setBorder(0);
        statementMonth.setPadding(5f);

        PdfPCell statement = new PdfPCell(new Phrase("ACCOUNT STATEMENT"));
        statement.setBorder(0);
        statement.setPadding(5f);

        PdfPCell customerName = new PdfPCell(new Phrase("Account Name: "+user.getFirstName().toUpperCase()+" "+user.getLastName().toUpperCase()));
        customerName.setBorder(0);
        customerName.setPadding(5f);

        PdfPCell space = new PdfPCell();

        PdfPCell customerAddress = new PdfPCell((new Phrase("Address: "+user.getAddress())));
        customerAddress.setBorder(0);
        customerAddress.setPadding(5f);

        statementDetails.addCell(customerName);
        statementDetails.addCell(statementMonth);
        statementDetails.addCell(statement);
        statementDetails.addCell(customerAddress);
        statementDetails.addCell(space);

        //-----------------------------------------------

        PdfPTable transactions = new PdfPTable(4);

        PdfPCell date = new PdfPCell(new Phrase("DATE"));
        date.setBackgroundColor(new BaseColor(70,90,242));
        date.setBorder(0);

        PdfPCell transactionType = new PdfPCell(new Phrase("TRANSACTION TYPE"));
        transactionType.setBackgroundColor(new BaseColor(70,90,242));
        transactionType.setBorder(0);

        PdfPCell amt = new PdfPCell(new Phrase("AMOUNT"));
        amt.setBackgroundColor(new BaseColor(70,90,242));
        amt.setBorder(0);

        PdfPCell status = new PdfPCell(new Phrase("STATUS"));
        status.setBackgroundColor(new BaseColor(70,90,242));
        status.setBorder(0);

        transactions.addCell(date);
        transactions.addCell(transactionType);
        transactions.addCell(amt);
        transactions.addCell(status);

        logList.forEach(log -> {
            transactions.addCell(new Phrase(log.getSentAt().toString()));
            transactions.addCell(new Phrase(log.getTransactionType()));
            transactions.addCell(new Phrase(String.valueOf(log.getAmount())));
            transactions.addCell(new Phrase(log.getStatus()));
        });
        //-------------------------------------------

        document.add(infoTable);
        document.add(statementDetails);
        document.add(transactions);

        document.close();

        EmailDetailsDTO email = EmailDetailsDTO.builder()
                        .recipient(user.getEmail())
                        .subject("STATEMENT OF ACCOUNT")
                        .messageBody("Dear "+user.getFirstName()+" "+user.getLastName()+",\n" +
                                     "We are pleased to attach a copy of your e-statement for the month of "+month+".")
                        .attachment(FILE)
                        .build();

        emailService.sendEmailwithPdf(email);

        return logList;
    }


}
