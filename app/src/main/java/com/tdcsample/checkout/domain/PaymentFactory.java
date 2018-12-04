package com.tdcsample.checkout.domain;

public class PaymentFactory {

    public static Payment createBillet() {
        Payment payment = new Payment();
        payment.setType(PaymentType.BILLET);

        return payment;
    }

    public static Payment createCreditCard(String number) {
        Payment payment = new Payment();
        payment.setType(PaymentType.BILLET);
        payment.setCreditCard(number);

        return payment;
    }
}
