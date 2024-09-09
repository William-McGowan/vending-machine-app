package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

public class VendingMachineTest {

    @Test
    public void TestGetMessageForProductType() {
        VendingMachine testMachine = new VendingMachine();

        String message = "Crunch Crunch, Yum!";
        Assert.assertEquals(message, testMachine.getMessageForProductType("Chip"));

        message = "Chew Chew, Yum!";
        Assert.assertEquals(message, testMachine.getMessageForProductType("Gum"));
    }

    @Test
    public void TestDispenseItem() {
        VendingMachine testMachine = new VendingMachine();
        Transactions.setBalance(0);

        Assert.assertEquals("Insufficient amount! Please make a deposit", testMachine.dispenseItem("A1"));

        Transactions.depositMoney(20);

        String message = "Potato Crisps Price: 3.05, Money remaining: 16.95\n" +
                "Crunch Crunch, Yum!";
        Assert.assertEquals(message, testMachine.dispenseItem("A1"));

        testMachine.dispenseItem("A1");
        testMachine.dispenseItem("A1");
        testMachine.dispenseItem("A1");
        testMachine.dispenseItem("A1");
        Assert.assertEquals("Item SOLD OUT!", testMachine.dispenseItem("A1"));

    }
}
