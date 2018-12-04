package com.tdcsample.checkout.config.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.tdcsample.checkout.gateways.dynamo.model.PurchaseModel;

import java.util.List;

import static com.amazonaws.util.Throwables.failure;

public class ListPurchaseModelMarshaller implements DynamoDBMarshaller<List<PurchaseModel>> {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter writer = mapper.writer();

    @Override
    public String marshall(List<PurchaseModel> purchaseModels) {
        try {
            return writer.writeValueAsString(purchaseModels);
        } catch (JsonProcessingException e) {
            throw failure(e,
                    "Unable to marshall the instance of " + purchaseModels.getClass()
                            + "into a string");
        }
    }

    @Override
    public List<PurchaseModel> unmarshall(Class<List<PurchaseModel>> aClass, String json) {
        final CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, PurchaseModel.class);
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            throw failure(e, "Unable to unmarshall the string " + json + "into " + aClass);
        }
    }
}
