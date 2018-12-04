package com.tdcsample.checkout.gateway.dynamo.assembler;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.tdcsample.checkout.domain.Order;
import com.tdcsample.checkout.gateway.dynamo.model.OrderModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OrderAssemblerTest {

    @InjectMocks
    private OrderAssembler orderAssembler;

    @Before
    public void setup() {
        FixtureFactoryLoader.loadTemplates("com.tdcsample.checkout.templates");
    }

    @Test
    public void executeValidAssemblingFromOrder(){
        //Given
        Order order = Fixture.from(Order.class).gimme("order 1");

        //When
        OrderModel assembled = orderAssembler.assemble(order);

        //Then
        assertThat(assembled).isNotNull();
        assertThat(assembled.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(assembled.getUsername()).isEqualTo(order.getUsername());
        assertThat(assembled.getValue()).isEqualTo(order.getValue());
        assertThat(assembled.getItems().size()).isEqualTo(order.getItems().size());
    }

    @Test
    public void executeValidAssemblingFromOrderModel(){
        //Given
        OrderModel orderModel = Fixture.from(OrderModel.class).gimme("order model 1");

        //When
        Order assembled = orderAssembler.assemble(orderModel);

        //Then
        assertThat(assembled).isNotNull();
        assertThat(assembled.getOrderNumber()).isEqualTo(orderModel.getOrderNumber());
        assertThat(assembled.getUsername()).isEqualTo(orderModel.getUsername());
        assertThat(assembled.getValue()).isEqualTo(orderModel.getValue());
        assertThat(assembled.getItems().size()).isEqualTo(orderModel.getItems().size());
    }
}
