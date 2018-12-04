package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.gateway.dynamo.model.OrderModel;
import com.tdcsample.checkout.gateway.dynamo.model.PurchaseModel;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderModelTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(OrderModel.class)
                .addTemplate("order model 1", new Rule() {
                    {
                        add("id", UUID.fromString("b3d6f112-b5e0-11e8-96f8-529269fb1459"));
                        add("orderNumber", 5435435L);
                        add("username", "carlosz");
                        add("items", has(1).of(PurchaseModel.class, "purchase model product 1"));
                        add("value", new BigDecimal("999.99"));
                    }
                })
                .addTemplate("order model 2", new Rule() {
                    {
                        add("id", UUID.fromString("bb7b92a6-b5e0-11e8-96f8-529269fb1459"));
                        add("orderNumber", 23423532L);
                        add("username", "carlosz");
                        add("items", has(1).of(PurchaseModel.class, "purchase model product 2"));
                        add("value", new BigDecimal("10.90"));
                    }
                });
    }
}
