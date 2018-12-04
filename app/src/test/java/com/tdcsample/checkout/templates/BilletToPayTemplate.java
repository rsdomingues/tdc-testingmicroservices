package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.domain.BilletToPay;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BilletToPayTemplate  implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(BilletToPay.class)
                .addTemplate("billet to due in 7 days", new Rule() {
                    {
                        add("number", "6523706156860610000000 1 00099999");
                        add("dueDate", LocalDate.now().plusDays(7));
                        add("value", new BigDecimal("999.99"));
                    }
                });
    }
}
