package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.gateway.http.jsons.ItemRequest;

import java.math.BigDecimal;

public class ItemRequestTemplate  implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(ItemRequest.class)
                .addTemplate(
                        "1 product for 200",
                        new Rule() {
                            {
                                add("code", "SKU-123-432");
                                add("quantity", 1);
                                add("value", BigDecimal.valueOf(200));
                            }
                        })
                .addTemplate(
                        "3 product for 27.90",
                        new Rule() {
                            {
                                add("code", "SKU-322-222");
                                add("quantity", 3);
                                add("value", BigDecimal.valueOf(27.90));
                            }
                        })
                .addTemplate(
                        "5 product for 132",
                        new Rule() {
                            {
                                add("code", "SKU-341-568");
                                add("quantity", 5);
                                add("value", BigDecimal.valueOf(132));
                            }
                        });
    }
}
