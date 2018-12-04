package com.tdcsample.checkout.gateway.dynamo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.tdcsample.checkout.conf.dynamo.UUIDMarshaller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "product")
public class ProductModel implements Serializable {

    private static final long serialVersionUID = 3004703626452808651L;

    @Id
    @DynamoDBHashKey(attributeName = "Id")
    @DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
    private UUID id;

    @DynamoDBAttribute(attributeName = "Name")
    private String name;

    @DynamoDBAttribute(attributeName = "Value")
    private BigDecimal value;

}
