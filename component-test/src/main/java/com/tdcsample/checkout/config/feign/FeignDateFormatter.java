package com.tdcsample.checkout.config.feign;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class FeignDateFormatter implements Formatter<Date> {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return formatter.parse(text);
    }

    @Override
    public String print(Date date, Locale locale) {
        return formatter.format(date);
    }
}