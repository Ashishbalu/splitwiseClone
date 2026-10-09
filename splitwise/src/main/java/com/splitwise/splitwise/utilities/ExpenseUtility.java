package com.splitwise.splitwise.utilities;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ExpenseUtility {
    public static BigDecimal convertToPaisa(BigDecimal amountInRupees){
        return amountInRupees.multiply(BigDecimal.valueOf(100));
    }

    public static BigDecimal convertToRupees(BigDecimal amountInRupees){
        return amountInRupees.divide(BigDecimal.valueOf(100));
    }
}
