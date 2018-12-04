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
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "stock")
public class StockModel implements Serializable {

    private static final long serialVersionUID = 4158873538380694739L;

    @Id
    @DynamoDBHashKey(attributeName = "Id")
    @DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
    private UUID id;

    @DynamoDBAttribute(attributeName = "ProductId")
    @DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
    private UUID productId;

    @DynamoDBAttribute(attributeName = "Total")
    private Integer total;

}
