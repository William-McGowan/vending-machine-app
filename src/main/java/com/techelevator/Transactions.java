package com.techelevator;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import static com.techelevator.Log.endTransactionLog;

public abstract class Transactions {
    private static double balance = 0;
    private static Scanner sc = new Scanner(System.in);

    public static void feedMoney() {
        String select = "";

        while (!select.equals("5")) {
            System.out.printf("\nCurrent Money Provided: $ %.2f.\n\n", getBalance());
            System.out.println("(1) Deposit 1 dollar");
            System.out.println("(2) Deposit 2 dollar");
            System.out.println("(3) Deposit 5 dollar");
            System.out.println("(4) Deposit 10 dollar");
            System.out.println("(5) Go Back");
            try {
                select = sc.nextLine();

                switch (select) {
                    case "1":
                        depositMoney(1);
                        Log.feedMoneyLog(1.00, getBalance());
                        break;
                    case "2":
                        depositMoney(2);
                        Log.feedMoneyLog(2.00, getBalance());
                        break;
                    case "3":
                        depositMoney(5);
                        Log.feedMoneyLog(5.00, getBalance());
                        break;
                    case "4":
                        depositMoney(10);
                        Log.feedMoneyLog(10.00, getBalance());
                        break;
                    case "5":
                        break;
                    default :
                        System.out.println("Invalid option, Please select again!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input, Please enter a valid number!.");
            }
        }
    }

    public static void depositMoney(double amount) {
        balance += amount;
    }

    public static double getBalance() {
        return balance;
    }

    public static void withdrawMoney(double amount) {
        balance -= amount;
    }

    public static Map<String, Integer> dispenseChange() {
        int numQuarters, numDimes, numNickels, balanceInCents;
        balanceInCents = (int) (getBalance() * 100);

        numQuarters = balanceInCents / 25;
        balanceInCents %= 25;
        numDimes = balanceInCents / 10;
        balanceInCents %= 10;
        numNickels = balanceInCents / 5;
        balanceInCents %= 5;

        return Map.of("Quarters",numQuarters, "Dimes", numDimes, "Nickels", numNickels);
    }

    public static void finishTransaction() {
        Map<String, Integer> change = dispenseChange();

        System.out.printf("Here is your change: %d Quarters, %d Dimes, %d Nickels\n",
                change.get("Quarters"), change.get("Dimes"), change.get("Nickels"));

        endTransactionLog(getBalance(), 0.0);
        setBalance(0.0);
    }

    public static void setBalance(double balance) {
        Transactions.balance = balance;
    }
}

