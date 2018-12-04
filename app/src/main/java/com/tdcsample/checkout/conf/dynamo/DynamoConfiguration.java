package com.tdcsample.checkout.conf.dynamo;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.ConversionSchemas;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.tdcsample.checkout.gateway.dynamo")
public class DynamoConfiguration {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  @Value("${amazon.dynamodb.url:http://localhost:8000/}")
  private String dynamoDBUrl;

  @Value("${amazon.aws.accesskey:local_access}")
  private String accessKey;

  @Value("${amazon.aws.secretkey:local_secret}")
  private String secretKey;

  @Value("${amazon.aws.region:us-east-1}")
  private String region;

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {
    LOGGER.trace("[starting] DynamoConfiguration.amazonDynamoDB()");

    AmazonDynamoDBClientBuilder dynamoBuilder = AmazonDynamoDBClientBuilder.standard();

    dynamoBuilder.withEndpointConfiguration(
        new AwsClientBuilder.EndpointConfiguration(dynamoDBUrl, region));

    if (StringUtils.hasValue(accessKey) && StringUtils.hasValue(secretKey)) {
      dynamoBuilder.withCredentials(amazonAWSCredentials());
    }

    return dynamoBuilder.build();
  }

  @Bean
  public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
    LOGGER.trace("[starting] DynamoConfiguration.dynamoDBMapper()");
    DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
    builder.setConversionSchema(ConversionSchemas.V2);
    return new DynamoDBMapper(amazonDynamoDB, builder.build());
  }

  @Bean
  public AWSCredentialsProvider amazonAWSCredentials() {
    return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
  }
}
