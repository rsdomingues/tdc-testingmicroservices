package com.tdcsample.checkout.gateway.http.jsons;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentRequest {

    @ApiModelProperty(value = "The payment method", required = true, example = "CARD")
    private String type;

    @ApiModelProperty(value = "The card number", example = "4111111111111111")
    private String cardValue;

}
