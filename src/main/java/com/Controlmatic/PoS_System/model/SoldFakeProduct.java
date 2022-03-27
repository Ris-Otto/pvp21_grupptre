package com.Controlmatic.PoS_System.model;

import java.util.Date;

public class SoldFakeProduct {

    /**
     * OK denhär klassen e bara här fö att vi sko testa hu de KAN se ut när man sen hämtar produkter från databasen
     * pga vi vet int hur de actually kmr se ut aaldöåa¨ldw
     *
     * KOMMER NOG tas bort sen I guess men jåå pls no kil
     */

    private final Date soldDate;

    public SoldFakeProduct(Date soldDate) {
        this.soldDate = soldDate;
    }

    public Date getSoldDate() {
        return soldDate;
    }
}
