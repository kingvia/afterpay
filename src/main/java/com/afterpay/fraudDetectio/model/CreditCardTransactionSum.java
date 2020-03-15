package com.afterpay.fraudDetectio.model;

import java.math.BigDecimal;
import java.util.LinkedList;

public class CreditCardTransactionSum {
    private LinkedList<CreditCardTransaction> transactions;
    private BigDecimal sum;

    public CreditCardTransactionSum() {
        transactions = new LinkedList<CreditCardTransaction>();
        sum = new BigDecimal(0);
    }

    /**
     * Add a credit card transaction.
     *
     * @param transaction
     */
    public void add(CreditCardTransaction transaction) {
        transactions.add(transaction);
        sum = sum.add(transaction.getAmount());
    }

    /**
     * Remove the first credit card transaction.
     *
     * @return Removed credit card transaction.
     */
    public CreditCardTransaction removeFirst() {
        CreditCardTransaction transaction = transactions.removeFirst();
        sum = sum.subtract(transaction.getAmount());
        return transaction;
    }

    public LinkedList<CreditCardTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(LinkedList<CreditCardTransaction> transactions) {
        this.transactions = transactions;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
