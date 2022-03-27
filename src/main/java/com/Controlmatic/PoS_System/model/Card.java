package com.Controlmatic.PoS_System.model;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.xml.bind.annotation.*;

@Entity
@XmlRootElement(name = "result")
@XmlType(name = "Card")
@XmlSeeAlso(Card.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Card extends Payment {

    private String paymentCardNumber;
    private String paymentCardType;
    private String bonusCardNumber;

    //Constructor for creating a new Card
    //Can be combined card or credit card
    //params: products = list of products to pay with card
    //        cardNumber = the number of the card
    //        cardType = the type of the card
    public Card(String cardNumber, String cardType) {
        this.paymentCardNumber = cardNumber;
        this.paymentCardType = cardType;
    }

    public Card() {

    }

    //Constructor for creating a new Bonus Card
    //params: products = list of products to get bonus points from
    //        cardNumber = the number of the bonus card
    /*public Card(List<Product> products, String cardNumber) {
        super.products = new ArrayList<>();
        super.products.addAll(products);
        this.cardNumber = cardNumber;
    }*/

    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }

    public String getPaymentCardType() { return paymentCardType; }

    public void setPaymentCardNumber(String paymentCardNumber) {
        this.paymentCardNumber = paymentCardNumber;
    }

    public void setPaymentCardType(String paymentCardType) {
        this.paymentCardType = paymentCardType;
    }

    public void setBonusCardNumber(String bonusCardNumber) {
        this.bonusCardNumber = bonusCardNumber;
    }

    /*public String getBonusCardNumber() {
        return bonusCardNumber;
    }*/
}
