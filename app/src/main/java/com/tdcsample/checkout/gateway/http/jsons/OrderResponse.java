package com.tdcsample.checkout.gateway.http.jsons;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse implements Serializable {

  private static final long serialVersionUID = -4104685421873319771L;

  @ApiModelProperty(value = "Order items")
  private String orderId;

  private String transactionId;
}
