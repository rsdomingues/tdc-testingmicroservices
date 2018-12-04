package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.gateways.dynamo.model.StockModel;

import java.util.UUID;

public class StockModelTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(StockModel.class)
                .addTemplate("model product 1 with one in stock", new Rule() {
                    {
                        add("id", UUID.fromString("1978f6b0-b5c2-11e8-96f8-529269fb1459"));
                        add("productId", UUID.fromString("cb85230e-b691-11e8-96f8-529269fb1459"));
                        add("total", 10);
                    }
                });
    }
}
