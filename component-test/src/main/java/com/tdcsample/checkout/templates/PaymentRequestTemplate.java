package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.domain.PaymentRequest;

public class PaymentRequestTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(PaymentRequest.class)
                .addTemplate(
                        "VISA",
                        new Rule() {
                            {
                                add("type", "CARD");
                                add("cardValue", "4012888888881881");
                            }
                        })
                .addTemplate(
                        "MasterCard",
                        new Rule() {
                            {
                                add("type", "CARD");
                                add("cardValue", "5105105105105100");
                            }
                        })
                .addTemplate(
                        "BILLET",
                        new Rule() {
                            {
                                add("type", "BILLET");
                                add("cardValue", "");
                            }
                        });
    }
}
