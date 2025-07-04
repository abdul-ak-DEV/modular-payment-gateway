package com.payment.launcher;

import com.payment.api.PaymentProcessor;

import java.util.List;
import java.util.ServiceLoader;
import java.util.Scanner;


public class PaymentLauncher {

  public static void main (String[] args){
      ServiceLoader<com.payment.api.PaymentProcessor> loader = ServiceLoader.load(PaymentProcessor.class);
      List<PaymentProcessor> processors = loader.stream()
              .map(ServiceLoader.Provider::get)
              .toList();

      if (processors.isEmpty()) {
          System.out.println("No PaymentProcessor found");
      }

      System.out.println(" Choose a payment method:");

      for (int i = 0; i < processors.size(); i++) {
          System.out.println((i+1 ) + ". " + processors.get(i).name());

      }

      System.out.print("Enter your choice: ");

      Scanner scanner = new Scanner(System.in);
      int choice = scanner .nextInt();
      if (choice < 1 || choice > processors.size()) {
          System.out.println("❌ Invalid choice. Please enter between 1 and " + processors.size());
          return;
      }

      if(choice >=1 && choice <= processors.size()){
          PaymentProcessor selectedProcessor = processors.get(choice - 1);
          System.out.print("Enter the amount: ₹");
          double amount = scanner.nextInt();
          selectedProcessor.pay(amount);
      }

  }
}