module payment.upi{
    requires payment.api;

    provides com.payment.api.PaymentProcessor
            with com.payment.upi.UpiPaymentProcessor;
}