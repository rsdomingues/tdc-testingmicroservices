package com.tdcsample.checkout.gateway.dynamo.assembler;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.tdcsample.checkout.domain.Stock;
import com.tdcsample.checkout.gateway.dynamo.model.ProductModel;
import com.tdcsample.checkout.gateway.dynamo.model.StockModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StockAssemblerTest {

    @InjectMocks
    private StockAssembler stockAssembler;

    @Before
    public void setup() {
        FixtureFactoryLoader.loadTemplates("com.tdcsample.checkout.templates");
    }

    @Test
    public void executeValidAssemblingStock() {
        //Given
        StockModel stock = Fixture.from(StockModel.class).gimme("model product 1 with one in stock");
        ProductModel product = Fixture.from(ProductModel.class).gimme("model product moto x 4");

        //When
        Stock assembled = stockAssembler.assemble(stock, product);

        //Then
        assertThat(assembled).isNotNull();
        assertThat(assembled.getId()).isEqualTo(product.getId());
        assertThat(assembled.getProduct().getId()).isEqualTo(product.getId());
        assertThat(assembled.getTotal()).isEqualTo(stock.getTotal());
    }
}
