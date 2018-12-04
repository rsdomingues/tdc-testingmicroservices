package com.tdcsample.checkout.config.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;

import java.util.UUID;

public class UUIDMarshaller implements DynamoDBMarshaller<UUID> {

    @Override
    public String marshall(UUID uuid) {
        return uuid.toString();
    }

    @Override
    public UUID unmarshall(Class<UUID> aClass, String strUuid) {
        return UUID.fromString(strUuid);
    }
}
