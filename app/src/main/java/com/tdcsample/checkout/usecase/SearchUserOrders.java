package com.tdcsample.checkout.usecase;

import com.tdcsample.checkout.domain.Order;
import com.tdcsample.checkout.gateway.OrderGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SearchUserOrders {

    private OrderGateway orderGateway;

    public SearchUserOrders(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public List<Order> execute(String username) {
        return orderGateway.findAllByUsername(username);
    }
}
