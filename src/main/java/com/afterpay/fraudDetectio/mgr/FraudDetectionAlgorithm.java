package com.afterpay.fraudDetectio.mgr;

import com.afterpay.fraudDetectio.model.CreditCardTransaction;
import com.afterpay.fraudDetectio.model.CreditCardTransactionSum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FraudDetectionAlgorithm {
    public static long ONE_DAY = 24 * 60 * 60 * 1000;

    private HashMap<String, CreditCardTransactionSum> runningTotal;

    public FraudDetectionAlgorithm() {
        runningTotal = new HashMap<String, CreditCardTransactionSum>();
    }

    /**
     * Return list of fraudulent credit card number from specified credit card transactions.
     */
    public List<String> checkFraudulent(List<String> transactionList, float max) {
        List<String> results = new ArrayList<String>();
        for (String s : transactionList) {
            CreditCardTransaction transaction = null;
            try {
                transaction = CreditCardTransactionMgr.parse(s);
            } catch (Exception e) {
                System.out.println("Input Ignored: " + e.toString());
                continue;
            }
            if (results.contains(transaction.getCreditCardNumber())) {
                continue;
            }
            if (isFraudulent(transaction, max)) {
                results.add(transaction.getCreditCardNumber());
                runningTotal.remove(transaction.getCreditCardNumber());
            }
        }
        return results;
    }

    /**
     * Return if the credit card number from specified credit card transactions.
     * <p>
     * A credit card will be identified as fraudulent if the sum of amounts for a unique hashed credit card number
     * over a 24 hour sliding window period exceeds the price threshold.
     */
    private boolean isFraudulent(CreditCardTransaction transaction, float max) {
        CreditCardTransactionSum sum = runningTotal.get(transaction.getCreditCardNumber());
        if (sum == null) {
            sum = new CreditCardTransactionSum();
            sum.add(transaction);
            runningTotal.put(transaction.getCreditCardNumber(), sum);
            return false;
        }
        while (sum.getTransactions().size() > 0 &&
                transaction.getTimestamp().getTime() - sum.getTransactions().getFirst().getTimestamp().getTime() > ONE_DAY) {
            sum.removeFirst();
        }
        sum.add(transaction);
        return sum.getSum().doubleValue() > max;
    }
}
