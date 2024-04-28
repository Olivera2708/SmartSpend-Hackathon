package com.example.smartspend.model;

import com.google.gson.annotations.Expose;

public class Transaction {
    @Expose
    private String name;
    @Expose
    private double value;
    @Expose
    private String currency;
    @Expose
    private String date;
    @Expose
    private String type;

    public Transaction() {
    }

    public Transaction(String name, int value, String currency, String date, String type) {
        this.name = name;
        this.value = value;
        this.currency = currency;
        this.date = date;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
