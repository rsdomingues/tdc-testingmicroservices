package com.tdcsample.checkout.gateway;

import com.tdcsample.checkout.domain.Payment;
import com.tdcsample.checkout.domain.PaymentStatus;

public interface PaymentGateway {

    PaymentStatus process(Payment payment, double total);

}
