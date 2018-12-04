package com.tdcsample.checkout.gateway;

import com.tdcsample.checkout.domain.Order;

import java.util.List;
import java.util.UUID;

public interface OrderGateway {

    Order register(Order order);

    List<Order> findAllByUsername(String username);

    Order findById(UUID orderId);

}
