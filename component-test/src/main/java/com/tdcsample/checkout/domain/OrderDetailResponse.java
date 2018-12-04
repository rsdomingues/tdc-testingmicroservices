package com.tdcsample.checkout.domain;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDetailResponse implements Serializable {

  private String productCode;
  private Integer quantity;
  private BigDecimal value;

}
