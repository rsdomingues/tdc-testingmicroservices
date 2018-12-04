package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.domain.Purchase;

import java.math.BigDecimal;

public class PurchaseTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Purchase.class)
                .addTemplate("purchase product 1", new Rule() {
                    {
                        add("productCode", "2833dc40-b5c4-11e8-96f8-529269fb1459");
                        add("quantity", 1);
                        add("value", new BigDecimal("999.99"));
                    }
                })
                .addTemplate("purchase product 2", new Rule() {
                    {
                        add("productCode", "4a12bd2c-b5c4-11e8-96f8-529269fb1459");
                        add("quantity", 2);
                        add("value", new BigDecimal("10.90"));
                    }
                });
    }
}
