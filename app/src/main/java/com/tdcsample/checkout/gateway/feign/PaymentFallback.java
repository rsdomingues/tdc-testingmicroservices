package com.tdcsample.checkout.gateway.feign;

import com.tdcsample.checkout.gateway.feign.jsons.PaymentRequest;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentResponse;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallback implements PaymentClient {

    @Override
    public PaymentResponse process(PaymentRequest request){
        PaymentResponse response = new PaymentResponse();
        response.setStatus(PaymentStatus.NOT_SENT);
        response.setTransactionId("internal-12345");
        response.setDetails("");
        return response;
    }
}
