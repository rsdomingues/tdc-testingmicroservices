package com.tdcsample.checkout.gateways.feign;

import com.tdcsample.checkout.domain.OrderRequest;
import com.tdcsample.checkout.domain.OrderResponse;
import com.tdcsample.checkout.domain.UserOrdersResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(value = "checkoutorder")
public interface CheckoutClient {

  @RequestMapping(method = RequestMethod.POST, value = "/api/v1/order")
  OrderResponse placeOrder(@RequestBody OrderRequest request, @RequestHeader("user") String username);

  @RequestMapping(method = RequestMethod.GET, value = "/api/v1/orders/{username}")
  List<UserOrdersResponse> getOrdersByUser(@PathVariable(value = "user") String username);
}
