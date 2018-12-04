package com.tdcsample.checkout.conf.dynamo;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.tdcsample.checkout.gateway.dynamo.ProductRepository;
import com.tdcsample.checkout.gateway.dynamo.StockRepository;
import com.tdcsample.checkout.gateway.dynamo.model.OrderModel;
import com.tdcsample.checkout.gateway.dynamo.model.ProductModel;
import com.tdcsample.checkout.gateway.dynamo.model.StockModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DynamoInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoInitializer.class);

    @Autowired
    private DynamoDBMapper amazonDynamoDB;

    @Autowired
    private AmazonDynamoDB dynamoDBMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOGGER.trace("Entering createDatabaseTablesIfNotExist()");
        //@formatter:off
        List<Class> entities = Arrays.asList(
                StockModel.class,
                ProductModel.class,
                OrderModel.class
        );
        //@formatter:on
        for (Class entity : entities) {
            generateTable(entity);
        }
    }

    private void generateTable(Class entity) {
        CreateTableRequest request = amazonDynamoDB
                .generateCreateTableRequest(entity)
                .withProvisionedThroughput(
                        new ProvisionedThroughput(1L, 1L)
                );

        try {
            DescribeTableResult result = dynamoDBMapper.describeTable(request.getTableName());
            LOGGER.info("Table status {}, {}", request.getTableName(), result.getTable().getTableStatus());
        } catch (ResourceNotFoundException expectedException) {
            CreateTableResult result = dynamoDBMapper.createTable(request);
            LOGGER.info("Table creation triggered {}, {}", request.getTableName(), result.getTableDescription().getTableStatus());
        }
    }

}