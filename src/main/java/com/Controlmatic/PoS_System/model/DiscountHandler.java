package com.Controlmatic.PoS_System.model;

import java.util.Objects;

public class DiscountHandler {

    public static double calculateDiscount(Product product, double discount) {
        double discountedPrice = product.getPrice()*((100-discount)/100);
        return Math.round(discountedPrice*100.0)/100.0; // Math.round for rounding to two decimals
    }

    // Used to create a new product object with a discount

    public static Product makeDiscountedProduct(Product product, double discount) {
        // Calculating discounted price and adding the discount percentage to the name
        double discountedPrice = calculateDiscount(product, discount);  // Do we still want it to have the same ID?
        if(discount > 0) {
            product.setDiscount(discount);
        }
        product.setPrice(discountedPrice);


        return product;
    }

}
