package com.tdcsample.checkout.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentRequest {

    private String type;

    private String cardValue;

}
