package com.tdcsample.checkout.gateway.dynamo;

import com.tdcsample.checkout.domain.Product;
import com.tdcsample.checkout.gateway.ProductGateway;
import com.tdcsample.checkout.gateway.conf.DynamoDBRepositoryTest;
import com.tdcsample.checkout.gateway.dynamo.model.ProductModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductGatewayTest extends DynamoDBRepositoryTest {

    @Autowired
    private ProductGateway productGateway;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        productRepository.deleteAll();

        List<ProductModel> productsModel = from(ProductModel.class).gimme(2,
                "model product moto x 4", "model product cellphone case moto x 4");
        productRepository.saveAll(productsModel);
    }

    @Test
    public void testFindProductByCode() {
        // GIVEN
        Product productExpected = from(Product.class).gimme("moto x 4");

        String productCode = productExpected.getId().toString();

        // WHEN
        Product productActual = productGateway.findByCode(productCode);

        // THEN
        assertThat(productActual).isNotNull().isEqualTo(productExpected);
    }
}
