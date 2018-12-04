package com.tdcsample.checkout.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Order {

    private UUID id;

    @NonNull
    private Long orderNumber;

    @NonNull
    private String username;

    @NonNull
    private List<Purchase> items;

    @NonNull
    private BigDecimal value;

    @NotNull String transactionId;

    // ajuste para rodar o ci
    private BilletToPay billet;

}
