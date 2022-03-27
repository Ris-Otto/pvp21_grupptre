package com.Controlmatic.PoS_System.model;

public class ChangeCalculator {

    public static double calculateChange(double price, double cash) {
        // return Math.round((price-cash)*100.0)/100.0; // rounding off to two decimals
        return Math.round((price-cash)*100.0/5.0)*5.0/100.0; // rounding off to .x0 or .x5 cents
    }

}
