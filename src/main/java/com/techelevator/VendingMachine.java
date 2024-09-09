package com.techelevator;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import static com.techelevator.Log.*;
import static com.techelevator.Transactions.*;

public class VendingMachine {
    private Map<String, Inventory> itemsMap = loadInventory();
    Scanner sc = new Scanner(System.in);
    private double totalSale = 0;

    public void mainMenu() {
        String select = "";

        while (!select.equals("3")) {
            System.out.println("\n(1) Display Vending Machine Items");
            System.out.println("(2) Purchase");
            System.out.println("(3) Exit");

            try {
                select = sc.nextLine();

                switch (select) {
                    case "1":
                        displayItems();
                        break;
                    case "2":
                        purchaseMenu();
                        break;
                    case "3":
                        System.exit(0);
                        break;
                    case "4":
                        salesReport(totalSale);
                        break;
                    default:
                        System.out.println("Invalid option, Please select again!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input, Please enter a valid number!.");
            }
        }
    }

    public void displayItems() {
        for (var entry : itemsMap.entrySet()) {
            String name = entry.getValue().getName();
            int quantity = entry.getValue().getQuantity();

            if (quantity == 0) {
                System.out.printf("%-20s | SOLD OUT\n", name);
            } else {
                System.out.printf("%-20s | %d\n", name, quantity);
            }
        }
    }

    public void purchaseMenu() {
        String select = "";

        while (!select.equals("3")) {
            System.out.printf("\nCurrent Money Provided: $ %.2f\n\n", getBalance());
            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction");
            try {
                select = sc.nextLine();

                switch (select) {
                    case "1":
                        feedMoney();
                        break;
                    case "2":
                        selectProduct();
                        break;
                    case "3":
                        finishTransaction();
                        break;
                    default:
                        System.out.println("Invalid option, Please select again!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input, Please enter a valid number!.");
            }
        }
    }

    public void selectProduct() {
        for (var entry : itemsMap.entrySet()) {
            String code = entry.getValue().getCode();
            String name = entry.getValue().getName();
            double price = entry.getValue().getPrice();

            System.out.printf("%s | %-20s | $ %.2f\n",code, name, price);
        }

        System.out.print("Enter a product code: ");
        String select = sc.nextLine();

        if (itemsMap.containsKey(select)) {
            System.out.println(dispenseItem(select));
        } else System.out.println("Item Doesn't exist");
    }

    public String dispenseItem(String code) {
        int quantity = itemsMap.get(code).getQuantity();
        double balance = getBalance();
        double price = itemsMap.get(code).getPrice();
        String name = itemsMap.get(code).getName();
        String type = itemsMap.get(code).getType();

        if (quantity == 0)
            return "Item SOLD OUT!";
        else if (balance >= price) {
            withdrawMoney(price);
            itemsMap.get(code).soldOne();
            String message = getMessageForProductType(type);

            purchaseLog(name, code, price, getBalance());
            saveSales(name);
            totalSale += price;

            return String.format("%s Price: %.2f, Money remaining: %.2f\n%s",
                    name, price, getBalance(), message);
        } else return "Insufficient amount! Please make a deposit";
    }

    public String getMessageForProductType(String type) {
        System.out.println();
        switch (type) {
            case "Chip":
                return "Crunch Crunch, Yum!";
            case "Candy":
                return "Munch Munch, Yum!";
            case "Drink":
                return "Glug Glug, Yum!";
            case "Gum":
                return "Chew Chew, Yum!";
            default:
                return "";
        }
    }

}
