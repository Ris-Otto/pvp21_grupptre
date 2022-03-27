package com.Controlmatic.PoS_System.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@XmlRootElement(name = "result")
@XmlType(name = "Cash")
@XmlSeeAlso(Cash.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Cash extends Payment {



    public Cash() {

    }

    //Band-aid :)
    public static String createUnMarshallable(double subTotal) {
        return "<result><subtotal>" + subTotal +"</subtotal><paymentType>Cash</paymentType></result>";
    }

    // enumerations
    @XmlTransient
    public enum Coin {
        FIVE_CENTS (0.05),
        TEN_CENTS (0.10),
        TWENTY_CENTS (0.20),
        FIFTY_CENTS (0.50),
        ONE_EURO (1.00),
        TWO_EURO (2.00);

        private final double value;

        Coin(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    @XmlTransient
    public enum Note {
        FIVE_NOTE (5.00),
        TEN_NOTE (10.00),
        TWENTY_NOTE (20.00),
        FIFTY_NOTE (50.00),
        ONE_HUNDRED_NOTE (100.00),
        TWO_HUNDRED_NOTE (200.00),
        FIVE_HUNDRED_NOTE (500.00);

        private final double value;

        Note(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }



}
