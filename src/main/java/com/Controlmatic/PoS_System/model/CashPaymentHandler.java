package com.Controlmatic.PoS_System.model;

public class CashPaymentHandler {

    //supposed to handle changeListener in CustomerWindowApplication
    //idk how to do this but basically has some fields that correspond to current value and last value or smt
    //and update DialogTextArea based on that
    //essentially -> if(value == 0) return -lastValue;

    private double currentValue;
    private double lastValue;

    public double getCurrentValue() {//returns currentValue if it is greater than zero -> returns the negative of lastValue if currentValue is zero
        //glorified undo method
        if(currentValue < lastValue) {
            return -(lastValue-currentValue);
        }
        return currentValue-lastValue;
    }

    public void setCurrentValue(double currentValue) {
        lastValue = this.currentValue;
        this.currentValue = currentValue;
    }
}
