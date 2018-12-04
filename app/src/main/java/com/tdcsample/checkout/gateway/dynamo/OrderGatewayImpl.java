package com.tdcsample.checkout.gateway.dynamo;

import com.tdcsample.checkout.domain.Order;
import com.tdcsample.checkout.domain.exception.ValidationException;
import com.tdcsample.checkout.gateway.OrderGateway;
import com.tdcsample.checkout.gateway.dynamo.assembler.OrderAssembler;
import com.tdcsample.checkout.gateway.dynamo.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderGatewayImpl implements OrderGateway {

    private OrderRepository orderRepository;


    @Autowired
    public OrderGatewayImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order register(Order order) {
        OrderModel orderModel = orderRepository.save(OrderAssembler.assemble(order));
        return converterToOrder(orderModel);
    }

    @Override
    public List<Order> findAllByUsername(String username) {
        return orderRepository.findAllByUsername(username).stream()
                .map(this::converterToOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Order findById(UUID orderId) {
        Optional<OrderModel> order = orderRepository.findById(orderId);
        return converterToOrder(order.orElseThrow(() -> new ValidationException("Order Not Found")));
    }

    private Order converterToOrder(OrderModel orderModel) {
        return OrderAssembler.assemble(orderModel);
    }
}
