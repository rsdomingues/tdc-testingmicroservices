package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentRequest;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentType;

import java.math.BigDecimal;

public class PaymentRequestJsonTemplate implements TemplateLoader {

        @Override
        public void load() {
        Fixture.of(PaymentRequest.class)
                .addTemplate(
                        "VISA",
                        new Rule() {
                            {
                                add("paymentType", PaymentType.CARD);
                                add("totalValue", BigDecimal.valueOf(99.9));
                                add("installments", 1);
                                add("cardNumber", "4012888888881881");
                            }
                        })
                .addTemplate(
                        "MasterCard",
                        new Rule() {
                            {
                                add("paymentType", PaymentType.CARD);
                                add("totalValue", BigDecimal.valueOf(399.9));
                                add("installments", 3);
                                add("cardNumber", "5105105105105100");
                            }
                        })
                .addTemplate(
                        "BILLET",
                        new Rule() {
                            {
                                add("paymentType", PaymentType.BILLET);
                                add("totalValue", BigDecimal.valueOf(99.9));
                                add("installments", 1);
                            }
                        });
    }
}
