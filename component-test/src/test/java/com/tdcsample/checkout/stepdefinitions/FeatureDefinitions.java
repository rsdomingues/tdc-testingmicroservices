package com.tdcsample.checkout.stepdefinitions;

import com.tdcsample.checkout.ApplicationConfiguration;
import com.tdcsample.checkout.config.ff4j.Features;
import cucumber.api.java.en.Given;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = ApplicationConfiguration.class)
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class FeatureDefinitions {

  private FF4j ff4j;

  @Autowired
  public FeatureDefinitions(FF4j ff4j) {
    this.ff4j = ff4j;
  }

  @Given("^the feature ([^\"]*) is disabled$")
  public void theFeatureIsDisabled(String featureName) throws Throwable {
    ff4j.disable(Features.valueOf(featureName).getKey());
  }

  @Given("^the feature ([^\"]*) is enabled")
  public void theFeatureIsEnabled(String featureName) throws Throwable {
    ff4j.enable(Features.valueOf(featureName).getKey());
  }
}
