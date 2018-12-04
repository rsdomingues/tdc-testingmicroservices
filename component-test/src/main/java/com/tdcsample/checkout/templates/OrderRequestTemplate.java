package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.domain.ItemRequest;
import com.tdcsample.checkout.domain.OrderRequest;
import com.tdcsample.checkout.domain.PaymentRequest;

public class OrderRequestTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(OrderRequest.class)
                .addTemplate(
                        "One Moto X4, paid with VISA",
                        new Rule() {
                            {
                                add("items", has(1).of(ItemRequest.class, "One Moto X4"));
                                add("payment", one(PaymentRequest.class, "VISA"));
                            }
                        })
                .addTemplate(
                        "Invalid order with VISA",
                        new Rule() {
                            {
                                add("items", has(0).of(ItemRequest.class));
                                add("payment", one(PaymentRequest.class, "VISA"));
                            }
                        })
                .addTemplate(
                        "Invalid order with no payment",
                        new Rule() {
                            {
                                add("items", has(0).of(ItemRequest.class));
                            }
                        })
                .addTemplate(
                        "One Moto X4, paid with BILLET",
                        new Rule() {
                            {
                                add("items", has(1).of(ItemRequest.class, "One Moto X4"));
                                add("payment", one(PaymentRequest.class, "BILLET"));
                            }
                        });
    }
}
