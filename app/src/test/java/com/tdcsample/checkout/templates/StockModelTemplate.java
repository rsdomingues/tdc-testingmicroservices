package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.gateway.dynamo.model.StockModel;

import java.util.UUID;

public class StockModelTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(StockModel.class)
                .addTemplate("model product 1 with one in stock", new Rule() {
                    {
                        add("id", UUID.fromString("1978f6b0-b5c2-11e8-96f8-529269fb1459"));
                        add("productId", UUID.fromString("2833dc40-b5c4-11e8-96f8-529269fb1459"));
                        add("total", 1);
                    }
                })
                .addTemplate("model product 2 with one in stock", new Rule() {
                    {
                        add("id", UUID.fromString("0feb5b9c-b5c2-11e8-96f8-529269fb1459"));
                        add("productId", UUID.fromString("4a12bd2c-b5c4-11e8-96f8-529269fb1459"));
                        add("total", 1);
                    }
                });
    }
}
