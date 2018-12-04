package com.tdcsample.checkout.domain;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse implements Serializable {

  private static final long serialVersionUID = -4104685421873319771L;

  private String orderId;

  private String transactionId;
}
