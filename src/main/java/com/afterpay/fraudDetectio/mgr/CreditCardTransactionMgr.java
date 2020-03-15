package com.afterpay.fraudDetectio.mgr;

import com.afterpay.fraudDetectio.model.CreditCardTransaction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class CreditCardTransactionMgr {
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

    public static CreditCardTransaction parse(String creditCardTransaction) throws Exception {
        List<String> result = Arrays.asList(creditCardTransaction.split(","));
        if (result == null || result.size() != 3) {
            throw new Exception("Invalid input [" + creditCardTransaction + "]");
        }
        CreditCardTransaction transaction = new CreditCardTransaction(result.get(0).trim());
        transaction.setTimestamp(new Timestamp(format.parse(result.get(1).trim()).getTime()));
        transaction.setAmount(new BigDecimal(result.get(2).trim()));
        return transaction;
    }
}
