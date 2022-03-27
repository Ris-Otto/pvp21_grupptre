package com.Controlmatic.PoS_System.model;


import java.util.List;

public class TotalSum {


    // calculateTotalSum räknar totala summan då en produkt läggs till eller tas bort
    public static double calculateTotalSum(List<Product> products){ //products är de skannade produkterna
        double totalSum = 0;
        for (Product p : products) {
            totalSum = calculateNewTotalSum(p, totalSum);
        }
        return totalSum;
    }

    // calculateNewTotalSum hämtar en produktspris och addedar det till totala summan
    public static double calculateNewTotalSum(Product product, double previousTotal) {
        return previousTotal + product.getPrice();
    }
}
