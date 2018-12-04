package com.tdcsample.checkout.domain.exception;

import com.tdcsample.checkout.domain.Product;
import lombok.Getter;

@Getter
public class StockWithoutProductException extends RuntimeException {

    private static final long serialVersionUID = -9175696078007658525L;

    private final transient Product product;
    private final Integer quantityInStock;

    public StockWithoutProductException(String message, Product product, Integer quantityInStock) {
        super(message);

        this.product = product;
        this.quantityInStock = quantityInStock;
    }
}
