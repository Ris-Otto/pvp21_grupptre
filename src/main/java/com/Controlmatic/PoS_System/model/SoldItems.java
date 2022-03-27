package com.Controlmatic.PoS_System.model;


import java.util.List;
import java.util.Date; //ska vi ha dehä formatet??


public class SoldItems {

    // Tanken e att vi tar in en lista me alla sålda produkter från databasen, men idk hur än :-)

    private List<SoldFakeProduct> soldProducts; //dehä ska tas från databasen me sålda producter vet ej hur
    private int productAmount=0;


    // Checkar om en produkt är köpt inom ett tidsintervall, ska sedan displaya produkterna

    public int amountOfProducts(Date startDate, Date endDate) {

        for (SoldFakeProduct s: soldProducts) {
            if(checkIfBetweenDates(startDate, endDate, s.getSoldDate())) {
                productAmount++;
                //add ti nån till lista som sen displayas blabla
            }
        }

        return productAmount;

    }

    // För att se om ett datum är inom ett visst tidsintervall

    public boolean checkIfBetweenDates(Date startDate, Date endDate, Date date){

        return (date.getTime() >= startDate.getTime() && date.getTime() <= endDate.getTime());

    }



}


