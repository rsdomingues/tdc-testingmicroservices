package com.tdcsample.checkout.domain.exception;

import lombok.Getter;

@Getter
public class NotPaidException extends RuntimeException {

    private static final long serialVersionUID = -6849300447989602519L;

    public NotPaidException(String message) {
        super(message);
    }

}
