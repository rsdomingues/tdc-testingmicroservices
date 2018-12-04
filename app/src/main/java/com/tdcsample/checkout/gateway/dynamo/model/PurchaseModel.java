package com.tdcsample.checkout.gateway.dynamo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseModel implements Serializable{

    private static final long serialVersionUID = -2847258243104084133L;

    private UUID productId;
    private Integer quantity;
    private BigDecimal value;

}
