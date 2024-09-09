package com.techelevator;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Log {
    private static final Date DATE = new Date();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
    private static final SimpleDateFormat FILE_DATE = new SimpleDateFormat("MM-dd-yyyy HH;mm");
    public static Map<String, Integer> sales = new HashMap<>();

    public static Map<String, Inventory> loadInventory() {
        Map<String, Inventory> itemsMap = new HashMap<>();
        final String INVENTORY_FILE = "vendingmachine.csv";

        try (var br = new BufferedReader(new FileReader(INVENTORY_FILE))) {

            while (br.ready()) {
                String line = br.readLine();

                String[] itemData = line.split("\\|");

                if (itemData.length != 4) {
                    System.err.println("Invalid data format " + line);
                    continue;
                }

                String name = itemData[1];
                String code = itemData[0];
                double price = Double.parseDouble(itemData[2]);
                String type = itemData[3];

                var product = new Inventory(name, code, price, type);
                itemsMap.put(code, product);
                sales.put(name, 0);
            }

            return itemsMap;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void feedMoneyLog(Double addedAmount, Double balance) {
        logWriter(String.format("%-22s FEED MONEY: $%.2f $%.2f\n", DATE_FORMAT.format(DATE), addedAmount, balance));
    }
    
    public static void purchaseLog(String product, String code, Double price, Double balance) {
        logWriter(String.format("%-22s %-18s %s $%.2f $%.2f\n",DATE_FORMAT.format(DATE), product, code, price, balance));
    }

    public static void endTransactionLog(Double changeReturned, Double balance) {
       logWriter(String.format("%-22s GIVE CHANGE: $%.2f $%.2f\n",DATE_FORMAT.format(DATE), changeReturned, balance));

    }

    public static void logWriter(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Log.txt", true))) {
            bw.write(message);
        } catch (IOException e) {
            System.err.println("Log file not found");
        }
    }

    public static void salesReport(double totalSales) {
        String salesReportFile = String.format("Sales %s", FILE_DATE.format(DATE));

        try (var bw = new BufferedWriter(new FileWriter(salesReportFile))) {
            for (var entry : sales.entrySet()) {
                bw.write(String.format("%-20s|%d\n", entry.getKey(), entry.getValue()));
            }

            bw.write(String.format("**TOTAL SALES** $%.2f", totalSales));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveSales(String product) {
        sales.compute(product, (k, v) -> v+1);
    }

}
