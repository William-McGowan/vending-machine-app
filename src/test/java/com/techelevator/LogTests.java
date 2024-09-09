package com.techelevator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class LogTests {

    @Test
    public void testLoadInventory() {
        VendingMachine testMachine = new VendingMachine();
        Map<String, Inventory> itemsMap = Log.loadInventory();

        Assertions.assertTrue(itemsMap.containsKey("C1"));
        Assertions.assertTrue(itemsMap.containsKey("A1"));

        Assertions.assertEquals(5, itemsMap.get("D3").getQuantity());
        Assertions.assertEquals("Wonka Bar", itemsMap.get("B3").getName());
        Assertions.assertEquals("Gum", itemsMap.get("D1").getType());
        Assertions.assertEquals(1.45, itemsMap.get("A2").getPrice(), 0.0);

    }

    @Test
    public void testSaveSales() {
        VendingMachine testMachine = new VendingMachine();

        Assertions.assertEquals(0, Log.sales.get("Stackers"));

        Log.saveSales("Stackers");
        Assertions.assertEquals(1, Log.sales.get("Stackers"));
    }

}
