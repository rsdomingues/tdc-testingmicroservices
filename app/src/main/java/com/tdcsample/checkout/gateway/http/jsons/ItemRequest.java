package com.tdcsample.checkout.gateway.http.jsons;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequest {

    @ApiModelProperty(value = "The product SKU", example = "cb85230e-b691-11e8-96f8-529269fb1459")
    private String code;

    @ApiModelProperty(value = "Amount bought", required = true, example = "1")
    private Integer quantity;

    @ApiModelProperty(value = "Individual value", required = true, example = "999.99")
    private BigDecimal value;


}
