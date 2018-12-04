package com.tdcsample.checkout.gateway.dynamo;

import com.tdcsample.checkout.domain.Product;
import com.tdcsample.checkout.domain.exception.ValidationException;
import com.tdcsample.checkout.gateway.ProductGateway;
import com.tdcsample.checkout.gateway.dynamo.assembler.ProductAssembler;
import com.tdcsample.checkout.gateway.dynamo.model.ProductModel;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ProductGatewayImpl implements ProductGateway {

    private ProductRepository productRepository;

    public ProductGatewayImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findByCode(String code) {
        Optional<ProductModel> productModel = productRepository.findById(UUID.fromString(code));
        return ProductAssembler.assemble(productModel.orElseThrow(() -> new ValidationException("Product Not Found")));
    }
}
