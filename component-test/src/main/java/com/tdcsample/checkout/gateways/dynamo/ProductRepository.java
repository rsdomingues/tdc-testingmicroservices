package com.tdcsample.checkout.gateways.dynamo;

import com.tdcsample.checkout.gateways.dynamo.model.ProductModel;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

@EnableScan
public interface ProductRepository extends CrudRepository<ProductModel, UUID> {
}
