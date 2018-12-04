package com.tdcsample.checkout.stepdefinitions;

import br.com.six2six.fixturefactory.Fixture;
import com.tdcsample.checkout.ApplicationConfiguration;
import com.tdcsample.checkout.domain.OrderRequest;
import com.tdcsample.checkout.domain.OrderResponse;
import com.tdcsample.checkout.gateways.dynamo.ProductRepository;
import com.tdcsample.checkout.gateways.dynamo.StockRepository;
import com.tdcsample.checkout.gateways.dynamo.model.ProductModel;
import com.tdcsample.checkout.gateways.dynamo.model.StockModel;
import com.tdcsample.checkout.gateways.feign.CheckoutClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(classes = ApplicationConfiguration.class)
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class OrderDefinitions {

  private CheckoutClient checkoutClient;
  private Exception exceptionReceived;

  private OrderRequest request;
  private OrderResponse response;
  private String username;

  private StockRepository stockRepository;
  private ProductRepository productRepository;

  @Autowired
  public OrderDefinitions(CheckoutClient checkoutClient, StockRepository stockRepository, ProductRepository productRepository) {
    this.checkoutClient = checkoutClient;
    this.stockRepository = stockRepository;
    this.productRepository = productRepository;
  }

    @Given("^I have stock from product$")
    public void i_have_stock_from_product() {
        ProductModel motox4 = Fixture.from(ProductModel.class).gimme("model product moto x 4");
        StockModel stockFromMotox4 = Fixture.from(StockModel.class).gimme("model product 1 with one in stock");

        productRepository.save(motox4);
        stockRepository.save(stockFromMotox4);
    }

    @Given("^an order for \"([^\"]*)\"$")
    public void an_order_for(String order) throws Throwable {
        this.request = Fixture.from(OrderRequest.class).gimme(order);
    }

    @Given("^that is being purchased by \"([^\"]*)\"$")
    public void that_is_being_purchased_by(String username) throws Throwable {
        this.username = username;
    }

    @When("^I place the order$")
    public void iPlaceTheOrder(){
        try {
            this.response = this.checkoutClient.placeOrder(this.request,this.username);
        } catch (Exception e ){
            this.exceptionReceived = e;
        }

    }

    @Then("^I receive an confirmantion$")
    public void iReceiveAnConfirmantion(){
        assertThat(this.response).isNotNull();
        assertThat(this.response.getOrderId()).isNotBlank();
        assertThat(this.response.getTransactionId()).isNotBlank();
        assertThat(this.exceptionReceived).isNull();
    }

    @Then("^I receive an error")
    public void iReceiveAnError(){
        assertThat(this.response).isNull();
        assertThat(this.exceptionReceived).isNotNull();
    }


}
