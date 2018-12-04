package com.tdcsample.checkout.gateway.feign;

import com.tdcsample.checkout.domain.Payment;
import com.tdcsample.checkout.domain.PaymentStatus;
import com.tdcsample.checkout.domain.TransactionStatus;
import com.tdcsample.checkout.gateway.PaymentGateway;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentRequest;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentResponse;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentGatewayImpl implements PaymentGateway {

    private PaymentClient paymentClient;

    @Autowired
    public PaymentGatewayImpl(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @Override
    public PaymentStatus process(Payment payment, double total) {
        PaymentRequest request = new PaymentRequest();

        request.setCardNumber(payment.getCreditCard());
        request.setInstallments(1);
        request.setTotalValue(BigDecimal.valueOf(total));
        request.setPaymentType(PaymentType.valueOf(payment.getType().toString()));

        PaymentResponse response = this.paymentClient.process(request);


        return PaymentStatus.builder()
                .status(TransactionStatus.valueOf(response.getStatus().toString()))
                .transactionId(response.getTransactionId())
                .build();

    }
}
