package com.tdcsample.checkout.gateway.dynamo.assembler;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.tdcsample.checkout.domain.Purchase;
import com.tdcsample.checkout.gateway.dynamo.model.PurchaseModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseAssemblerTest {

    @InjectMocks
    private PurchaseAssembler purchaseAssembler;

    @Before
    public void setup() {
        FixtureFactoryLoader.loadTemplates("com.tdcsample.checkout.templates");
    }

    @Test
    public void executeValidAssemblingFromPurchase(){
        //Given
        Purchase purchase = Fixture.from(Purchase.class).gimme("purchase product 1");

        //When
        PurchaseModel assembled = purchaseAssembler.assemble(purchase);

        //Then
        assertThat(assembled).isNotNull();
        assertThat(assembled.getQuantity()).isEqualTo(purchase.getQuantity());
        assertThat(assembled.getValue()).isEqualTo(purchase.getValue());
        assertThat(assembled.getProductId().toString()).isEqualTo(purchase.getProductCode());
    }

    @Test
    public void executeValidAssemblingFromPurchaseModel(){
        //Given
        PurchaseModel purchase = Fixture.from(PurchaseModel.class).gimme("purchase model product 1");

        //When
        Purchase assembled = purchaseAssembler.assemble(purchase);

        //Then
        assertThat(assembled).isNotNull();
        assertThat(assembled.getQuantity()).isEqualTo(purchase.getQuantity());
        assertThat(assembled.getValue()).isEqualTo(purchase.getValue());
        assertThat(assembled.getProductCode()).isEqualTo(purchase.getProductId().toString());
    }
}
