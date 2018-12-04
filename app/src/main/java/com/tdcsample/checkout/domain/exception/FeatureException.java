package com.tdcsample.checkout.domain.exception;

import lombok.Getter;

@Getter
public class FeatureException extends RuntimeException {

    private static final long serialVersionUID = -2847258260604084133L;

    private final String name;
    private final transient Object[] msgParams;

    public FeatureException(String message, String name, Object... msgParams) {
        super(message);
        this.name = name;
        this.msgParams = msgParams;
    }

}
