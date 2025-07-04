package com.payment.api;


public interface PaymentProcessor {

    String name();

    void pay(double amount);
}