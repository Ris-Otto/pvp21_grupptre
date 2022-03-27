package com.Controlmatic.PoS_System.model;

import java.util.HashMap;

public class PriceLookupTable {

    //Probably not very nice but oh well

    private static HashMap<String, Double> priceTable = new HashMap<>();


    /**
     * Det här borde egentligen definieras i den verkliga databasen
     * men tyvärr har vi enbart en runtime db
     */
    public static void setPriceTable() {
        priceTable.putIfAbsent("Gurka", 5.0);
        priceTable.putIfAbsent("katt", 100.0);
        priceTable.putIfAbsent("beer", 10.0);
        priceTable.putIfAbsent("lapras", 1000.0);
    }

    public static double lookupPriceForItem(String name) {
        if(!priceTable.containsKey(name))
            return 0;
        return priceTable.get(name);
    }

    public static void definePriceForProduct(String name, double price) {
        priceTable.put(name, price);
    }

}
