package com.tdcsample.checkout.gateway.dynamo.assembler;

import com.tdcsample.checkout.domain.Product;
import com.tdcsample.checkout.gateway.dynamo.model.ProductModel;

import java.math.RoundingMode;

public class ProductAssembler {

    public static Product assemble(ProductModel productModel) {
        return Product.builder()
                .id(productModel.getId())
                .name(productModel.getName())
                .value(productModel.getValue().setScale(2, RoundingMode.CEILING))
                .build();
    }
}
