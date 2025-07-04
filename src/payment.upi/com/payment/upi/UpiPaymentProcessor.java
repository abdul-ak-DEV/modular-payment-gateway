package com.payment.upi;

import com.payment.api.PaymentProcessor;

public class UpiPaymentProcessor implements PaymentProcessor{
    
    @Override
    public String name(){
        return "UPI";
    }

    @Override
    public void pay(double amount){
        System.out.println("Processing ₹" + amount + " via UPI...");
        System.out.println("UPI Payment Successful.");
    }

}