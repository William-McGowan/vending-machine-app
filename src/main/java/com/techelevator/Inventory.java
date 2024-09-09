package com.techelevator;

public class Inventory {
    private final String name;
    private final String code;
    private final double price;
    private final String type;
    private int quantity;

    public Inventory(String name, String code, double price, String type) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.type = type;
        quantity = 5;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void soldOne() {
        quantity--;
    }
}


