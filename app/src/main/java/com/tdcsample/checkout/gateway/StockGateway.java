package com.tdcsample.checkout.gateway;

import com.tdcsample.checkout.domain.Product;
import com.tdcsample.checkout.domain.Stock;

public interface StockGateway {

    Stock getStock(Product product);

    boolean lowStock(Product product, int quantityToPurchase);
}
