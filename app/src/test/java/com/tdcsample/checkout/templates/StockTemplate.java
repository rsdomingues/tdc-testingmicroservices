package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.domain.Product;
import com.tdcsample.checkout.domain.Stock;

import java.util.UUID;

public class StockTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Stock.class)
                .addTemplate("product 1 with one in stock", new Rule() {
                    {
                        add("id", UUID.fromString("1978f6b0-b5c2-11e8-96f8-529269fb1459"));
                        add("product", one(Product.class, "moto x 4"));
                        add("total", 1);
                    }
                })
                .addTemplate("product 2 with one in stock", new Rule() {
                    {
                        add("id", UUID.fromString("0feb5b9c-b5c2-11e8-96f8-529269fb1459"));
                        add("product", one(Product.class, "cellphone case moto x 4"));
                        add("total", 1);
                    }
                });
    }
}
