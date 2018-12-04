package com.tdcsample.checkout.domain;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequest implements Serializable {

    private static final long serialVersionUID = 2415328239342194229L;

    @Getter
    @Setter
    private List<ItemRequest> items;

    @Getter
    @Setter
    private PaymentRequest payment;

}
