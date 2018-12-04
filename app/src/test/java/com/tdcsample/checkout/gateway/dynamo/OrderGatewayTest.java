package com.tdcsample.checkout.gateway.dynamo;

import com.tdcsample.checkout.domain.Order;
import com.tdcsample.checkout.domain.exception.ValidationException;
import com.tdcsample.checkout.gateway.OrderGateway;
import com.tdcsample.checkout.gateway.conf.DynamoDBRepositoryTest;
import com.tdcsample.checkout.gateway.dynamo.model.OrderModel;
import com.tdcsample.checkout.gateway.dynamo.model.ProductModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderGatewayTest extends DynamoDBRepositoryTest {

    @Autowired
    private OrderGateway orderGateway;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        orderRepository.deleteAll();

        List<OrderModel> ordersModel = from(OrderModel.class).gimme(2,
                "order model 1", "order model 2");
        orderRepository.saveAll(ordersModel);

        List<ProductModel> productsModel = from(ProductModel.class).gimme(2,
                "model product moto x 4", "model product cellphone case moto x 4");
        productRepository.saveAll(productsModel);
    }

    @Test
    public void testUsernameWithoutOrders() {
        // GIVEN
        String username = "eduardoz";

        // WHEN
        List<Order> orders = orderGateway.findAllByUsername(username);

        // THEN
        assertThat(orders).hasSize(0);
    }

    @Test
    public void testUsernameWithOrders() {
        // GIVEN
        String username = "carlosz";

        Order[] ordersExpected = from(Order.class).gimme(2, "order 1", "order 2").toArray(new Order[2]);

        // WHEN
        List<Order> orders = orderGateway.findAllByUsername(username);

        // THEN
        assertThat(orders).hasSize(2);
        assertThat(orders).containsExactlyInAnyOrder(ordersExpected);
    }

    @Test
    public void testRegisterOrder() {
        // GIVEN
        Order order = from(Order.class).gimme("order to save");

        // WHEN
        orderGateway.register(order);

        // THEN
        OrderModel orderActual = orderRepository.findById(order.getId()).get();
        assertThat(orderActual).isNotNull();
        assertThat(orderActual.getId()).isEqualTo(order.getId());
        assertThat(orderActual.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(orderActual.getUsername()).isEqualTo(order.getUsername());
        assertThat(orderActual.getItems()).hasSize(1);
        assertThat(orderActual.getValue()).isEqualTo(order.getValue().setScale(1, RoundingMode.CEILING));
    }

    @Test
    public void testFindByOrderId() {
        // GIVEN
        Order order = from(Order.class).gimme("order 1");

        // WHEN
        Order orderActual = orderGateway.findById(order.getId());

        // THEN
        assertThat(orderActual).isNotNull();
        assertThat(orderActual.getId()).isEqualTo(order.getId());
        assertThat(orderActual.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(orderActual.getUsername()).isEqualTo(order.getUsername());
        assertThat(orderActual.getItems()).hasSize(1);
        assertThat(orderActual.getValue()).isEqualTo(order.getValue());
    }

    @Test(expected = ValidationException.class)
    public void testFindByOrderIdNotExistOrder() {
        // WHEN
        Order orderActual = orderGateway.findById(UUID.randomUUID());
    }
}
