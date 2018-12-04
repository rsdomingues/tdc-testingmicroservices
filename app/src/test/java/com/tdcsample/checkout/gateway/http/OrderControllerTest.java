package com.tdcsample.checkout.gateway.http;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdcsample.checkout.domain.Order;
import com.tdcsample.checkout.domain.exception.NotPaidException;
import com.tdcsample.checkout.gateway.http.jsons.OrderRequest;
import com.tdcsample.checkout.gateway.http.jsons.OrderResponse;
import com.tdcsample.checkout.gateway.http.jsons.UserOrdersResponse;
import com.tdcsample.checkout.usecase.CheckoutOrder;
import com.tdcsample.checkout.usecase.SearchUserOrders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderControllerTest extends AbstractHttpTest {

  private MockMvc mockMvc;

  @Mock
  private CheckoutOrder checkoutOrder;

  @Mock
  private SearchUserOrders searchUserOrders;

  @InjectMocks private OrderController orderController;


  @Before
  public void setup() {
    mockMvc = buildMockMvcWithBusinessExecptionHandler(orderController);
    FixtureFactoryLoader.loadTemplates("com.tdcsample.checkout.templates");
  }

  @Test
  public void returnOkWhenValidRequest() throws Exception {
    //GIVEN an valid Request
    Order order = Fixture.from(Order.class).gimme("order 1");
    when(checkoutOrder.execute(any(), any())).thenReturn(order);

    OrderRequest validOrder = Fixture.from(OrderRequest.class).gimme("Valid order with VISA");

    //WHEN the controller is called with these request params
    MvcResult result = mockMvc
        .perform(
            post("/api/v1/order")
                    .content(asJsonString(validOrder))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("user", "someuser"))


    //THEN no errors are thrown and the status returned is OK
        .andExpect(status().isOk())
        .andReturn();

    // AND
    OrderResponse orderResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), OrderResponse.class);
    assertThat(orderResponse).isNotNull();
    assertThat(orderResponse.getOrderId()).isEqualTo(order.getOrderNumber().toString());
  }


  @Test
  public void returnErrorWheninvalidItemRequest() throws Exception {
    //GIVEN an valid Request
    OrderRequest validOrder = Fixture.from(OrderRequest.class).gimme("Invalid order with VISA");

    when(checkoutOrder.execute(any(), any())).thenThrow(Exception.class);

    //WHEN the controller is called with these request params
    mockMvc
            .perform(
                    post("/api/v1/order")
                            .content(asJsonString(validOrder))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))

            //THEN no errors are thrown and the status returned is NOT OK
            .andExpect(status().isInternalServerError())
            .andReturn();
  }

  @Test
  public void returnErrorWheninvalidPaymentRequest() throws Exception {
    //GIVEN an valid Request
    OrderRequest validOrder = Fixture.from(OrderRequest.class).gimme("Invalid order with no payment");

    when(checkoutOrder.execute(any(), any())).thenThrow(IllegalArgumentException.class);

    //WHEN the controller is called with these request params
    mockMvc
            .perform(
                    post("/api/v1/order")
                            .content(asJsonString(validOrder))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))

            //THEN no errors are thrown and the status returned is OK
            .andExpect(status().is(500))
            .andReturn();
  }

  @Test
  public void testUnauthorizedCreditCard() throws Exception {
    //GIVEN an valid Request
    OrderRequest validOrder = Fixture.from(OrderRequest.class).gimme("Invalid order with no payment");

    when(checkoutOrder.execute(any(), any())).thenThrow(NotPaidException.class);

    //WHEN the controller is called with these request params
    mockMvc
            .perform(
                    post("/api/v1/order")
                            .content(asJsonString(validOrder))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .header("user", "someuser"))

            //THEN no errors are thrown and the status returned is OK
            .andExpect(status().isUnprocessableEntity())
            .andReturn();
  }

  @Test
  public void returnListOfOrdersFromUsername() throws Exception {
    //GIVEN an valid Request
    List<Order> orders = Fixture.from(Order.class).gimme(2,"order 1", "order 2");

    List<Long> ordersNumberExpected = orders.stream().map(Order::getOrderNumber).collect(Collectors.toList());

    when(searchUserOrders.execute(any())).thenReturn(orders);

    //WHEN the controller is called with these request params
    MvcResult result = mockMvc
            .perform(
                    get("/api/v1/orders/carlosz")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))

            //THEN no errors are thrown and the status returned is OK
            .andExpect(status().isOk())
            .andReturn();

    // AND
    List<UserOrdersResponse> userOrdersResponseActual = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserOrdersResponse>>(){});
    assertThat(userOrdersResponseActual).hasSize(2);

    List<Long> ordersNumberActual = userOrdersResponseActual.stream().map(UserOrdersResponse::getOrderNumber).collect(Collectors.toList());

    assertThat(ordersNumberActual).containsAll(ordersNumberExpected);
  }

  @Test
  public void returnEmptyListOfOrdersFromUsername() throws Exception {
    //GIVEN an valid Request
    when(searchUserOrders.execute(any())).thenReturn(newArrayList());

    //WHEN the controller is called with these request params
    MvcResult result = mockMvc
            .perform(
                    get("/api/v1/orders/carlosz")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))

            //THEN no errors are thrown and the status returned is OK
            .andExpect(status().isOk())
            .andReturn();

    // AND
    List<UserOrdersResponse> userOrdersResponseActual = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserOrdersResponse>>(){});
    assertThat(userOrdersResponseActual).hasSize(0);
  }
}
