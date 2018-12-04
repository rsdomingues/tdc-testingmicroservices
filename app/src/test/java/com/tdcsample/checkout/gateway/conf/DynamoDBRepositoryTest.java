package com.tdcsample.checkout.gateway.conf;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.tdcsample.checkout.conf.dynamo.DynamoInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DynamoDBTestConfiguration.class, DynamoInitializer.class})
public class DynamoDBRepositoryTest {

    @Autowired
    private AmazonDynamoDB dynamoDB;

    @Before
    public void setup() {
        FixtureFactoryLoader.loadTemplates("com.tdcsample.checkout.templates");
    }

    @Test
    public void assertDynamoSpringInjection() {
        assertThat(dynamoDB, notNullValue());
    }
}
