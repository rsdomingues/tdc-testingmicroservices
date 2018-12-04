package com.tdcsample.checkout.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.tdcsample.checkout.domain.PaymentStatus;
import com.tdcsample.checkout.domain.TransactionStatus;

public class PaymentStatusTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(PaymentStatus.class)
                .addTemplate(
                        "NOT_SENT",
                        new Rule() {
                            {
                                add("transactionId", "123454546112324677");
                                add("status", TransactionStatus.NOT_SENT);
                            }
                        })
                .addTemplate(
                        "PAID",
                        new Rule() {
                            {
                                add("transactionId", "123454546112324677");
                                add("status", TransactionStatus.PAID);
                            }
                        })
                .addTemplate(
                        "FRAUD",
                        new Rule() {
                            {
                                add("transactionId", "123454546112324677");
                                add("status", TransactionStatus.FRAUD);
                            }
                        })
                .addTemplate(
                        "CANCELLED",
                        new Rule() {
                            {
                                add("transactionId", "123454546112324677");
                                add("status", TransactionStatus.CANCELLED);
                            }
                        })
                .addTemplate(
                        "INSUFFICIENT_FUNDS",
                        new Rule() {
                            {
                                add("transactionId", "123454546112324677");
                                add("status", TransactionStatus.INSUFFICIENT_FUNDS);
                            }
                        })
                .addTemplate(
                        "PENDING",
                        new Rule() {
                            {
                                add("transactionId", "123454546112324677");
                                add("status", TransactionStatus.PENDING);
                            }
                        });
    }
}
