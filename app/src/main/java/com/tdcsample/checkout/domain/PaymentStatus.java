package com.tdcsample.checkout.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class PaymentStatus {

    private String transactionId;

    private TransactionStatus status;
}
