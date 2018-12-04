package com.tdcsample.checkout.conf.feign;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import feign.Logger;
import feign.hystrix.SetterFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.tdcsample.checkout.gateway.feign")
public class FeignConfig {

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.BASIC;
  }

  @Bean
  public SpringWebClientErrorDecoder errorDecoder() {
    return new SpringWebClientErrorDecoder();
  }

  @Bean
  @ConditionalOnProperty(name = "feign.hystrix.enabled")
  public SetterFactory setterFactory() {
    return (target, method) ->
        HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(target.name()))
            .andCommandKey(HystrixCommandKey.Factory.asKey(target.name()));
  }
}
