package com.tdcsample.checkout.gateway.http;

import com.tdcsample.checkout.conf.log.LogKey;
import com.tdcsample.checkout.domain.Order;
import com.tdcsample.checkout.domain.Payment;
import com.tdcsample.checkout.domain.PaymentType;
import com.tdcsample.checkout.domain.Purchase;
import com.tdcsample.checkout.domain.ShoppingCart;
import com.tdcsample.checkout.gateway.http.jsons.ItemRequest;
import com.tdcsample.checkout.gateway.http.jsons.OrderDetailResponse;
import com.tdcsample.checkout.gateway.http.jsons.OrderRequest;
import com.tdcsample.checkout.gateway.http.jsons.OrderResponse;
import com.tdcsample.checkout.gateway.http.jsons.PaymentRequest;
import com.tdcsample.checkout.gateway.http.jsons.UserOrdersResponse;
import com.tdcsample.checkout.usecase.CheckoutOrder;
import com.tdcsample.checkout.usecase.SearchUserOrders;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.value;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Api(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

  private CheckoutOrder checkoutOrder;
  private SearchUserOrders searchUserOrders;

  @Autowired
  public OrderController(CheckoutOrder checkoutOrder, SearchUserOrders searchUserOrders) {
    this.checkoutOrder = checkoutOrder;
    this.searchUserOrders = searchUserOrders;
  }

  @ApiOperation(value = "Place Order")
  @ApiResponses(
          value = {
                  @ApiResponse(code = 200, message = "Order palced with success"),
                  @ApiResponse(code = 403, message = "Feature disabled"),
                  @ApiResponse(code = 408, message = "Request Timeout"),
                  @ApiResponse(code = 422, message = "Error placing order"),
                  @ApiResponse(code = 500, message = "Internal Server Error")
          }
  )
  @RequestMapping(path = "/order", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public OrderResponse placeOrder(@RequestBody OrderRequest request, @RequestHeader("user") String username) {
    log.info(
            "Placing new order with {} having payment method like {}",
            value(LogKey.AMOUNT_ITEMS.toString(), request.getItems()),
            value(LogKey.PAYMENT_METHOD.toString(), Optional.ofNullable(request.getPayment()).map(PaymentRequest::getType).orElse(null)));

    ShoppingCart shoppingCart = ShoppingCart.builder()
            .username(username)
            .items(
                    request.getItems().stream()
                            .map(this::createPurchase)
                            .collect(Collectors.toList())
            )
            .build();

    Payment payment = Optional.ofNullable(request.getPayment())
            .map(paymentRequest -> Payment.builder()
                    .creditCard(request.getPayment().getCardValue())
                    .type(PaymentType.valueOf(request.getPayment().getType()))
                    .build())
            .orElse(null);

    Order order = checkoutOrder.execute(shoppingCart, payment);

    return OrderResponse.builder().orderId(order.getOrderNumber().toString()).transactionId(order.getTransactionId()).build();
  }

  @ApiOperation(value = "Detail Order")
  @ApiResponses(
          value = {
                  @ApiResponse(code = 200, message = "List of Orders from username"),
                  @ApiResponse(code = 403, message = "Feature disabled"),
                  @ApiResponse(code = 408, message = "Request Timeout"),
                  @ApiResponse(code = 500, message = "Internal Server Error")
          }
  )
  @RequestMapping(path = "orders/{username}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public List<UserOrdersResponse> listOrders(@PathVariable String username) {
    log.info(
            "Listing all order from usename {}",
            value(LogKey.AMOUNT_ITEMS.toString(), username));

    List<Order> orders = searchUserOrders.execute(username);

    return orders.stream()
            .map(order -> {List<OrderDetailResponse> collect = order.getItems().stream()
                      .map(purchase -> OrderDetailResponse.builder()
                                .productCode(purchase.getProductCode())
                                .quantity(purchase.getQuantity())
                                .value(purchase.getValue())
                                .build())
                      .collect(Collectors.toList());
              return UserOrdersResponse.builder()
                      .id(order.getId())
                      .orderNumber(order.getOrderNumber())
                      .value(order.getValue())
                      .orderDetails(collect)
                      .build();
            })
            .collect(Collectors.toList());
  }

  private Purchase createPurchase(ItemRequest itemRequest) {
    return Purchase.builder()
            .productCode(itemRequest.getCode())
            .quantity(itemRequest.getQuantity())
            .value(itemRequest.getValue())
            .build();
  }
}
