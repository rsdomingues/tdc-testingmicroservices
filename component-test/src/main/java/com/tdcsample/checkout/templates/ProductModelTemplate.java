package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.gateways.dynamo.model.ProductModel;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductModelTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(ProductModel.class)
                .addTemplate("model product moto x 4", new Rule() {
                    {
                        add("id", UUID.fromString("cb85230e-b691-11e8-96f8-529269fb1459"));
                        add("name", "Moto X 4");
                        add("value", new BigDecimal("999.99"));
                    }
                });
    }
}
