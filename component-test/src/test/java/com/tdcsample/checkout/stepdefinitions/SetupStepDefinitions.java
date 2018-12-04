package com.tdcsample.checkout.stepdefinitions;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.tdcsample.checkout.ApplicationConfiguration;
import com.tdcsample.checkout.config.ff4j.Features;
import com.tdcsample.checkout.gateways.dynamo.OrderRepository;
import com.tdcsample.checkout.gateways.dynamo.ProductRepository;
import com.tdcsample.checkout.gateways.dynamo.StockRepository;
import cucumber.api.java.Before;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = ApplicationConfiguration.class)
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class SetupStepDefinitions {

  private StockRepository stockRepository;
  private ProductRepository productRepository;
  private OrderRepository orderRepository;

  private FF4j ff4j;

  @Autowired
  public SetupStepDefinitions(
          StockRepository stockRepository,
          ProductRepository productRepository,
          OrderRepository orderRepository,
      FF4j ff4j) {
    this.stockRepository = stockRepository;
    this.productRepository = productRepository;
    this.orderRepository = orderRepository;
    this.ff4j = ff4j;
  }

  @Before("@FixtureLoad")
  public void initializeFixtureTemplate() throws Throwable {
    FixtureFactoryLoader.loadTemplates("com.tdcsample.checkout.templates");
  }

//  @Before("@CleanStubby")
//  public void cleanupStubby() throws Throwable {
//    stubbyAmadeus.deleteAllServices();
//    stubbyVoeAzul.deleteAllServices();
//    stubbyViajeMais.deleteAllServices();
//  }

  @Before("@CleanDatabase")
  public void cleanupDatabase() throws Throwable {
    stockRepository.deleteAll();
    productRepository.deleteAll();
    orderRepository.deleteAll();
  }

  @Before("@EnableFeatures")
  public void enableFeatures() throws Throwable {
    for (Features feature : Features.values()) {
      ff4j.enable(feature.getKey());
    }
  }
}
