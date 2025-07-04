module payment.card{
    requires payment.api;

    provides com.payment.api.PaymentProcessor
            with com.payment.card.CardPaymentProcessor;
}