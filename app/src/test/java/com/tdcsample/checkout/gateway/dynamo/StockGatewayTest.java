package com.tdcsample.checkout.gateway.dynamo;

import com.tdcsample.checkout.domain.Product;
import com.tdcsample.checkout.domain.Stock;
import com.tdcsample.checkout.gateway.StockGateway;
import com.tdcsample.checkout.gateway.conf.DynamoDBRepositoryTest;
import com.tdcsample.checkout.gateway.dynamo.model.ProductModel;
import com.tdcsample.checkout.gateway.dynamo.model.StockModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.assertj.core.api.Assertions.assertThat;

public class StockGatewayTest extends DynamoDBRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockGateway stockGateway;

    @Before
    public void setUp() {
        stockRepository.deleteAll();

        List<StockModel> stocksModel = from(StockModel.class).gimme(2,
                "model product 1 with one in stock", "model product 2 with one in stock");
        stockRepository.saveAll(stocksModel);

        List<ProductModel> productsModel = from(ProductModel.class).gimme(2,
                "model product moto x 4", "model product cellphone case moto x 4");
        productRepository.saveAll(productsModel);
    }

    @Test
    public void testFindStockFromOneProduct() {
        // GIVEN
        Product product = from(Product.class).gimme("moto x 4");

        // WHEN
        Stock stock = stockGateway.getStock(product);

        // THEN
        assertThat(stock).isNotNull();
        assertThat(stock.getId()).isNotNull();
        assertThat(stock.getProduct()).isEqualTo(product);
        assertThat(stock.getTotal()).isEqualTo(1);
    }

    @Test
    public void testLowStockFromProduct() {
        // GIVEN
        Product product = from(Product.class).gimme("moto x 4");

        // WHEN
        stockGateway.lowStock(product, 1);

        // THEN
        StockModel stockModel = stockRepository.findByProductId(product.getId());

        assertThat(stockModel).isNotNull();
        assertThat(stockModel.getTotal()).isEqualTo(0);
    }
}
