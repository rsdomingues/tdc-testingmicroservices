package com.tdcsample.checkout.domain;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequest {

    private String code;

    private Integer quantity;

    private BigDecimal value;


}
