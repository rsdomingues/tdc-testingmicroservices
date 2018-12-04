package com.tdcsample.checkout.gateway.feign;

import com.tdcsample.checkout.gateway.feign.jsons.PaymentRequest;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "payment", fallback = PaymentFallback.class)
public interface PaymentClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/payment")
    PaymentResponse process(@RequestBody PaymentRequest request);
}
