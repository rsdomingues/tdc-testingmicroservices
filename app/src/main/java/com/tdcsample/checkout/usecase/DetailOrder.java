package com.tdcsample.checkout.usecase;

import com.tdcsample.checkout.domain.Order;
import com.tdcsample.checkout.gateway.OrderGateway;

import java.util.UUID;

public class DetailOrder {

    private OrderGateway orderGateway;

    public DetailOrder(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public Order execute(UUID orderId) {
        return orderGateway.findById(orderId);
    }
}
