package com.tdcsample.checkout.gateway.conf;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.tdcsample.checkout.gateway.dynamo")
@ComponentScan(basePackages = "com.tdcsample.checkout.gateway.dynamo")
public class DynamoDBTestConfiguration {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
    LOGGER.trace("[starting] DynamoConfiguration.amazonDynamoDB()");
    try {
      AmazonDynamoDB amazonDynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
      return amazonDynamoDB;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Bean
  public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
    LOGGER.trace("[starting] DynamoConfiguration.dynamoDBMapper()");
    return new DynamoDBMapper(amazonDynamoDB);
  }
}
