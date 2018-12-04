package com.tdcsample.checkout.domain;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Purchase {

    @NonNull
    private String productCode;

    @NonNull
    private Integer quantity;

    @NonNull
    private BigDecimal value;

}
