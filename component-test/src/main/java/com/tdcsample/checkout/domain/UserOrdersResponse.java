package com.tdcsample.checkout.domain;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserOrdersResponse implements Serializable {

  private UUID id;
  private Long orderNumber;
  private BigDecimal value;
  private List<OrderDetailResponse> orderDetails;

}
