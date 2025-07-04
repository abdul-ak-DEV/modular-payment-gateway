package com.payment.card;

import com.payment.api.PaymentProcessor;

public class CardPaymentProcessor implements PaymentProcessor {

    @Override
    public String name (){
        return "Credit Card";
    }

    @Override
    public void pay(double amount){
        System.out.println("Processing ₹" + amount + " from Credit Card...");
        System.out.println("Credit Card Payment Approved.");
    }
}