package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentResponse;
import com.tdcsample.checkout.gateway.feign.jsons.PaymentStatus;

public class PaymentResponseTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(PaymentResponse.class)
                .addTemplate(
                        "PAID",
                        new Rule() {
                            {
                                add("status", PaymentStatus.PAID);
                                add("details", "http://urltodetail/api/v1/details/10237819283791823");
                                add("transactionId", "10237819283791823");
                            }
                        })
                .addTemplate(
                        "FRAUD",
                        new Rule() {
                            {
                                add("status", PaymentStatus.FRAUD);
                                add("details", "http://urltodetail/api/v1/details/10237819283791823");
                                add("transactionId", "10237819283791823");
                            }
                        })
                .addTemplate(
                        "INSUFFICIENT_FUNDS",
                        new Rule() {
                            {
                                add("status", PaymentStatus.INSUFFICIENT_FUNDS);
                                add("details", "http://urltodetail/api/v1/details/10237819283791823");
                                add("transactionId", "10237819283791823");
                            }
                        });
    }
}
