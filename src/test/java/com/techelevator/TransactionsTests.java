package com.techelevator;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class TransactionsTests {

    @Test
    public void TestDispenseChange() {
        Transactions.setBalance(0);
        Transactions.depositMoney(3.65);

        Map<String, Integer> change = Transactions.dispenseChange();
        Assertions.assertEquals(14, change.get("Quarters"));
        Assertions.assertEquals(1, change.get("Dimes"));
        Assertions.assertEquals(1, change.get("Nickels"));

    }
}
