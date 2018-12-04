package com.tdcsample.checkout.gateway.dynamo.assembler;

import com.tdcsample.checkout.domain.Stock;
import com.tdcsample.checkout.gateway.dynamo.model.ProductModel;
import com.tdcsample.checkout.gateway.dynamo.model.StockModel;

public class StockAssembler {

    public static Stock assemble(StockModel stockModel, ProductModel productModel) {
        return Stock.builder()
                .id(productModel.getId())
                .product(ProductAssembler.assemble(productModel))
                .total(stockModel.getTotal())
                .build();
    }
}
