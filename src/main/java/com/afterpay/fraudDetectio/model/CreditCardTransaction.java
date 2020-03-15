package com.afterpay.fraudDetectio.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CreditCardTransaction {
    private String creditCardNumber;
    private Timestamp timestamp;
    private BigDecimal amount;

    public CreditCardTransaction(String creditCardNumber, Timestamp timestamp, BigDecimal amount) {
        this.creditCardNumber = creditCardNumber;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public CreditCardTransaction(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        timestamp = null;
        amount = new BigDecimal(0);
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
