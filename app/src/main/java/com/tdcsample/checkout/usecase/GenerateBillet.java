package com.tdcsample.checkout.usecase;

import com.tdcsample.checkout.conf.TdcSampleProperties;
import com.tdcsample.checkout.domain.BilletToPay;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.leftPad;

@Component
public class GenerateBillet {

    private Integer daysToDueDate;
    private BigDecimal additionPrice;

    public GenerateBillet(TdcSampleProperties tdcSampleProperties) {
        this.daysToDueDate = tdcSampleProperties.getBillet().getDaysDueDate();
        this.additionPrice = tdcSampleProperties.getBillet().getAdditionPrice();
    }

    public BilletToPay execute(Double valueToPay) {
        checkArgument(nonNull(valueToPay), "Value to pay cannot be null");

        BigDecimal totalValue = new BigDecimal(valueToPay).add(additionPrice);

        return BilletToPay.builder()
                .dueDate(LocalDate.now().plus(daysToDueDate, ChronoUnit.DAYS))
                .value(totalValue)
                .number(generateBilletNumber(totalValue))
                .build();
    }

    private String generateBilletNumber(BigDecimal totalValue) {
        long aNumber = generatePrefixBilletNumber();
        String sufixBilletNumber = generateSufixBilletNumber(totalValue);
        return format("%s0000000 1 %s", aNumber, sufixBilletNumber);
    }

    private long generatePrefixBilletNumber() {
        return (long)((Math.random() * 900000000000000L)+100000000000000L);
    }

    private String generateSufixBilletNumber(BigDecimal totalValue) {
        String valueWithoutDot = totalValue.toString().replace(".", EMPTY);
        return leftPad(valueWithoutDot, 8, "0");
    }
}
