package com.tdcsample.checkout.gateway.ff4j;

import com.tdcsample.checkout.conf.ff4j.Features;
import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeatureGatewayTest {

    @InjectMocks
    private FeatureGatewayImpl featureGateway;

    @Mock
    private FF4j ff4j;

    @Test
    public void featureEnabled() {
        Feature feature = new Feature("Feature1", true);
        when(ff4j.getFeature(anyString())).thenReturn(feature);

        boolean featureEnable = featureGateway.isFeatureEnable(Features.BILLET_PAYMENT);

        assertThat(featureEnable, equalTo(true));
    }

    @Test
    public void featureDisabled() {
        Feature feature = new Feature("Feature1", false);
        when(ff4j.getFeature(anyString())).thenReturn(feature);

        boolean featureEnable = featureGateway.isFeatureEnable(Features.BILLET_PAYMENT);

        assertThat(featureEnable, equalTo(false));
    }
}
