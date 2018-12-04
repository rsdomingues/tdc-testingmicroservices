package com.tdcsample.checkout.gateway.http.jsons;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequest implements Serializable {

    private static final long serialVersionUID = 2415328239342194229L;

    @Getter
    @Setter
    @ApiModelProperty(value = "Order items", required = true)
    private List<ItemRequest> items;

    @Getter
    @Setter
    @ApiModelProperty(value = "Order PaymentRequest method", required = true)
    private PaymentRequest payment;

}
