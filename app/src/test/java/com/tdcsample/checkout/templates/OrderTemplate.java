package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.domain.Order;
import com.tdcsample.checkout.domain.Purchase;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Order.class)
                .addTemplate("order 1", new Rule() {
                    {
                        add("id", UUID.fromString("b3d6f112-b5e0-11e8-96f8-529269fb1459"));
                        add("orderNumber", 5435435L);
                        add("username", "carlosz");
                        add("items", has(1).of(Purchase.class, "purchase product 1"));
                        add("value", new BigDecimal("999.99"));
                    }
                })
                .addTemplate("order 2", new Rule() {
                    {
                        add("id", UUID.fromString("bb7b92a6-b5e0-11e8-96f8-529269fb1459"));
                        add("orderNumber", 23423532L);
                        add("username", "carlosz");
                        add("items", has(1).of(Purchase.class, "purchase product 2"));
                        add("value", new BigDecimal("10.90"));
                    }
                })
                .addTemplate("order to save", new Rule() {
                    {
                        add("id", UUID.fromString("020b118c-b5f1-11e8-96f8-529269fb1459"));
                        add("orderNumber", 7878776L);
                        add("username", "carlosz");
                        add("items", has(1).of(Purchase.class, "purchase product 2"));
                        add("value", new BigDecimal("10.90"));
                    }
                });
    }
}
