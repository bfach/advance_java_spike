package com.xyzcorp.java8;

/**
 * Created by bfach on 10/10/16.
 */
public class TaxRate {

    private final int year;
    private final double taxRate;


    public TaxRate(int year, double taxRate) {
        this.year = year;
        this.taxRate = taxRate;
    }

    public int getYear() {
        return year;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public double apply(int subtotal){
        return (subtotal * taxRate) + subtotal;
    }
}
