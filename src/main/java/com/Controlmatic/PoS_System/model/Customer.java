package com.Controlmatic.PoS_System.model;



/**
 * No time to integrate! Ni får ha anonyma kunder, vi vill ha bättre betalt !!
 */
public class Customer {

    double bonusPoints;
    Card card;
    Card bonusCard;

    public Customer(Card card) {
        this.card = card;
    }

    public Customer(Card card, Card bonusCard) {
        bonusPoints = 0;
        this.card = card;
        this.bonusCard = bonusCard;
    }

    public void addBonusPoints(double amount) {
        bonusPoints += amount;
    }

    public Card getCard() {
        return card;
    }

    public double getBonusPoints() {
        return bonusPoints;
    }

    public Card getBonusCard() {
        return bonusCard;
    }
}