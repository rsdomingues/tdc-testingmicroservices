package com.tdcsample.checkout.gateway.dynamo.assembler;

import com.tdcsample.checkout.domain.Order;
import com.tdcsample.checkout.gateway.dynamo.model.OrderModel;

import java.math.RoundingMode;
import java.util.stream.Collectors;

public class OrderAssembler {

    public static Order assemble(OrderModel orderModel) {
        return Order.builder()
                .id(orderModel.getId())
                .username(orderModel.getUsername())
                .orderNumber(orderModel.getOrderNumber())
                .items(orderModel.getItems().stream()
                        .map(PurchaseAssembler::assemble)
                        .collect(Collectors.toList()))
                .value(orderModel.getValue().setScale(2, RoundingMode.CEILING))
                .build();
    }

    public static OrderModel assemble(Order order) {
        return OrderModel.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .username(order.getUsername())
                .items(order.getItems().stream()
                        .map(PurchaseAssembler::assemble)
                        .collect(Collectors.toList()))
                .value(order.getValue().setScale(2, RoundingMode.CEILING))
                .build();
    }
}
