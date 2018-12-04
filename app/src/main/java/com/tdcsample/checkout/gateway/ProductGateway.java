package com.tdcsample.checkout.gateway;

import com.tdcsample.checkout.domain.Product;

public interface ProductGateway {

    Product findByCode(String code);

}
