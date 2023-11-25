package com.wellTech.pivotbank.utils;

import java.time.Year;
import java.util.Random;

public class AccNumbGenerator {

    public static String generateAccountNumber() {

        int randomNumber =(int) Math.floor(Math.random() * (999999-100000+1)+100000);
        String year = String.valueOf(Year.now());
        String accountNumber = year + randomNumber;
        return  accountNumber;
    }

}
