package com.afterpay.fraudDetectio.mgr;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FraudDetectionAlgorithmTest {
    public static long ONE_HOUR = 60 * 60 * 1000;

    @Test
    public void checkFraudulentNoExcced() {
        List<String> transactions = new ArrayList<String>();
        String creditCardNumber = "10d7ce2f43e35fa57d1bbf8b1e2";
        // 800 1st 24 hour
        transactions.add(creditCardNumber + ",2020-03-05T01:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T02:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T03:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T04:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T08:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T20:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T22:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-06T01:22:21,100");
        transactions.add(creditCardNumber + ",2020-03-06T02:22:21,100");
        transactions.add(creditCardNumber + ",2020-03-06T03:22:21,100");
        transactions.add(creditCardNumber + ",2020-03-06T04:22:21,100");
        transactions.add(creditCardNumber + ",2020-03-06T05:22:21,100");
        transactions.add(creditCardNumber + ",2020-03-06T06:22:21,100");

        FraudDetectionAlgorithm algorithm = new FraudDetectionAlgorithm();
        List<String> results = algorithm.checkFraudulent(transactions, 1000);

        assertEquals("No credit card found", 0, results.size());
    }

    @Test
    public void checkFraudulentExceedHappyFlow() {
        List<String> transactions = new ArrayList<String>();
        String creditCardNumber = "10d7ce2f43e35fa57d1bbf8b1e2";
        // 800 1st 24 hour
        transactions.add(creditCardNumber + ",2020-03-05T01:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T02:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T03:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T04:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T08:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T20:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T22:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-06T01:22:21,100.01");

        FraudDetectionAlgorithm algorithm = new FraudDetectionAlgorithm();
        List<String> results = algorithm.checkFraudulent(transactions, 800);

        assertEquals("Only one credit card", 1, results.size());
        assertEquals("Credit card number", creditCardNumber, results.get(0));
    }

    @Test
    public void checkFraudulentExact24Hours() {
        List<String> transactions = new ArrayList<String>();
        String creditCardNumber = "10d7ce2f43e35fa57d1bbf8b1e2";
        // 800 1st 24 hour
        transactions.add(creditCardNumber + ",2020-03-05T01:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T02:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T03:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T04:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T08:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T20:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T22:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-06T01:22:21,100");

        FraudDetectionAlgorithm algorithm = new FraudDetectionAlgorithm();
        List<String> results = algorithm.checkFraudulent(transactions, 800);

        assertEquals("No credit card found", 0, results.size());
    }

    @Test
    public void checkFraudulentExceedNextDay() {
        List<String> transactions = new ArrayList<String>();
        String creditCardNumber = "10d7ce2f43e35fa57d1bbf8b1e2";
        // 800 1st 24 hour then 200 in 25th hour

        transactions.add(creditCardNumber + ",2020-03-05T01:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T02:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T03:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T04:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T08:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T20:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-05T22:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-06T01:22:21,100");
        transactions.add(creditCardNumber + ",2020-03-06T02:22:21,300");
        transactions.add(creditCardNumber + ",2020-03-06T04:22:22,100");
        transactions.add(creditCardNumber + ",2020-03-06T05:22:22,100");

        FraudDetectionAlgorithm algorithm = new FraudDetectionAlgorithm();
        List<String> results = algorithm.checkFraudulent(transactions, 800);

        assertEquals("Only one credit card", 1, results.size());
        assertEquals("Credit card number", creditCardNumber, results.get(0));
    }

    @Test
    public void checkFraudulentExceedInOneTransaction() {
        List<String> transactions = new ArrayList<String>();
        String creditCardNumber = "10d7ce2f43e35fa57d1bbf8b1e2";
        // 800 1st 24 hour then 200 in 25th hour

        transactions.add(creditCardNumber + ",2020-03-05T01:22:22,1");
        transactions.add(creditCardNumber + ",2020-03-05T02:22:22,1");
        transactions.add(creditCardNumber + ",2020-03-05T03:22:22,1");
        transactions.add(creditCardNumber + ",2020-03-05T04:22:22,1");
        transactions.add(creditCardNumber + ",2020-03-05T08:22:22,1");
        transactions.add(creditCardNumber + ",2020-03-05T20:22:22,1");
        transactions.add(creditCardNumber + ",2020-03-05T22:22:22,1");
        transactions.add(creditCardNumber + ",2020-03-06T01:22:21,1");
        transactions.add(creditCardNumber + ",2020-03-06T02:22:21,1");
        transactions.add(creditCardNumber + ",2020-03-06T04:22:22,1");
        transactions.add(creditCardNumber + ",2020-03-06T05:22:22,900");

        FraudDetectionAlgorithm algorithm = new FraudDetectionAlgorithm();
        List<String> results = algorithm.checkFraudulent(transactions, 800);

        assertEquals("Only one credit card", 1, results.size());
        assertEquals("Credit card number", creditCardNumber, results.get(0));
    }

    @Test
    public void checkFraudulentMultiCreditCardOnlyOneExceed() {
        List<String> transactions = new ArrayList<String>();
        String creditCardNumber1 = "10d7ce2f43e35fa57d1bbf8b1e2";
        String creditCardNumber2 = "20d7ce2f43e35fa57d1bbf8b1e2";
        // 800 1st 24 hour then 200 in 25th hour

        transactions.add(creditCardNumber1 + ",2020-03-05T01:22:22,1");
        transactions.add(creditCardNumber1 + ",2020-03-05T02:22:22,1");
        transactions.add(creditCardNumber1 + ",2020-03-05T03:22:22,100");
        transactions.add(creditCardNumber1 + ",2020-03-05T04:22:22,1");
        transactions.add(creditCardNumber1 + ",2020-03-05T08:22:22,1");
        transactions.add(creditCardNumber1 + ",2020-03-05T20:22:22,100");
        transactions.add(creditCardNumber1 + ",2020-03-05T22:22:22,1");
        transactions.add(creditCardNumber1 + ",2020-03-06T01:22:21,100");
        transactions.add(creditCardNumber1 + ",2020-03-06T02:22:21,1");
        transactions.add(creditCardNumber1 + ",2020-03-06T04:22:22,1");
        transactions.add(creditCardNumber1 + ",2020-03-06T05:22:22,900");

        transactions.add(creditCardNumber2 + ",2020-03-05T01:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T02:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T03:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T04:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T08:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T20:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T22:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-06T01:22:21,1");
        transactions.add(creditCardNumber2 + ",2020-03-06T02:22:21,1");
        transactions.add(creditCardNumber2 + ",2020-03-06T04:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-06T05:22:22,900");

        FraudDetectionAlgorithm algorithm = new FraudDetectionAlgorithm();
        List<String> results = algorithm.checkFraudulent(transactions, 1000);

        assertEquals("Only 1 credit card", 1, results.size());
        assertTrue("Credit card number 1", results.contains(creditCardNumber1));
    }

    @Test
    public void checkFraudulentMultiCreditCardAllExceed() {
        List<String> transactions = new ArrayList<String>();
        String creditCardNumber1 = "10d7ce2f43e35fa57d1bbf8b1e2";
        String creditCardNumber2 = "20d7ce2f43e35fa57d1bbf8b1e2";
        // 800 1st 24 hour then 200 in 25th hour

        transactions.add(creditCardNumber1 + ",2020-03-05T01:22:22,100");
        transactions.add(creditCardNumber1 + ",2020-03-05T02:22:22,100");
        transactions.add(creditCardNumber1 + ",2020-03-05T03:22:22,100");
        transactions.add(creditCardNumber1 + ",2020-03-05T04:22:22,100");
        transactions.add(creditCardNumber1 + ",2020-03-05T05:22:22,100");
        transactions.add(creditCardNumber1 + ",2020-03-05T08:22:22,100");
        transactions.add(creditCardNumber1 + ",2020-03-05T20:22:22,100");
        transactions.add(creditCardNumber1 + ",2020-03-05T22:22:22,100");
        transactions.add(creditCardNumber1 + ",2020-03-06T01:22:21,100");
        transactions.add(creditCardNumber1 + ",2020-03-06T02:22:21,100");
        transactions.add(creditCardNumber1 + ",2020-03-06T04:22:22,100");
        transactions.add(creditCardNumber1 + ",2020-03-06T05:22:22,100");

        transactions.add(creditCardNumber2 + ",2020-03-05T01:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T02:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T03:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T04:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T08:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T20:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-05T22:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-06T01:22:21,1");
        transactions.add(creditCardNumber2 + ",2020-03-06T02:22:21,1");
        transactions.add(creditCardNumber2 + ",2020-03-06T04:22:22,1");
        transactions.add(creditCardNumber2 + ",2020-03-06T05:22:22,900");

        FraudDetectionAlgorithm algorithm = new FraudDetectionAlgorithm();
        List<String> results = algorithm.checkFraudulent(transactions, 800);

        assertEquals("Two credit card", 2, results.size());
        assertTrue("Credit card number 1", results.contains(creditCardNumber1));
        assertTrue("Credit card number 2", results.contains(creditCardNumber2));
    }
}