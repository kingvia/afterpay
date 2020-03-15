package com.afterpay.fraudDetectio.mgr;

import com.afterpay.fraudDetectio.model.CreditCardTransaction;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class CreditCardTransactionMgrTest {

    @Test
    public void parseSuccess() throws Exception {
        String s = "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00";

        CreditCardTransaction transaction = CreditCardTransactionMgr.parse(s);

        assertEquals("Credit Card Number", "10d7ce2f43e35fa57d1bbf8b1e2", transaction.getCreditCardNumber());
        assertEquals("Timestamp", Timestamp.valueOf("2014-04-29 13:15:54"), transaction.getTimestamp());
        assertEquals("Amount", 10.00, transaction.getAmount().doubleValue(), 0);
    }

    @Test
    public void parseWrongTimeStamp() {
        String s = "10d7ce2f43e35fa57d1bbf8b1e2, 20140429131554, 10.00";

        CreditCardTransaction transaction = null;
        boolean isException = false;
        try {
            transaction = CreditCardTransactionMgr.parse(s);
        } catch (Exception e) {
            e.printStackTrace();
            isException = true;
        }
        assertTrue("Invalid input cause exception", isException);
    }

    @Test
    public void parseWrongAmount() {
        String s = "10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.a0";

        CreditCardTransaction transaction = null;
        boolean isException = false;
        try {
            transaction = CreditCardTransactionMgr.parse(s);
        } catch (Exception e) {
            e.printStackTrace();
            isException = true;
        }
        assertTrue("Invalid input cause exception", isException);
    }

    @Test
    public void parseSuccessWithSpaces() throws Exception {
        String s = "     10d7ce2f43e35fa57d1b1f8b1e2   ,    2020-04-29T13:15:54    ,  10.11     ";

        CreditCardTransaction transaction = CreditCardTransactionMgr.parse(s);

        assertEquals("Credit Card Number", "10d7ce2f43e35fa57d1b1f8b1e2", transaction.getCreditCardNumber());
        assertEquals("Timestamp", Timestamp.valueOf("2020-04-29 13:15:54"), transaction.getTimestamp());
        assertEquals("Amount", 10.11, transaction.getAmount().doubleValue(), 0);
    }
}