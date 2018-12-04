package com.tdcsample.checkout.gateway;

import com.tdcsample.checkout.conf.ff4j.Features;

@FunctionalInterface
public interface FeatureGateway {

    boolean isFeatureEnable(Features features);

}
