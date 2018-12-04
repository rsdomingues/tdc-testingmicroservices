package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.domain.ItemRequest;

import java.math.BigDecimal;

public class ItemRequestTemplate  implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(ItemRequest.class)
                .addTemplate(
                        "One Moto X4",
                        new Rule() {
                            {
                                add("code", "cb85230e-b691-11e8-96f8-529269fb1459");
                                add("quantity", 1);
                                add("value", BigDecimal.valueOf(999.99));
                            }
                        });
    }
}
