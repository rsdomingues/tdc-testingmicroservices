package com.tdcsample.checkout.config.ff4j;

import lombok.extern.slf4j.Slf4j;
import org.ff4j.FF4j;
import org.ff4j.redis.RedisConnection;
import org.ff4j.store.EventRepositoryRedis;
import org.ff4j.store.FeatureStoreRedis;
import org.ff4j.store.PropertyStoreRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class FF4jConfiguration {

    private static final String DEFAULT_CONSOLE = "/ff4j-console/*";
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Value("${ff4j.redis.server:localhost}")
    private String redisServer;

    @Value("${ff4j.redis.port:6379}")
    private Integer redisPort;

    @Bean
    public FF4j configureFF4j() {
        FF4j ff4j = new FF4j();

        log.info("[starting] FF4jConfiguration.configureFF4j()");

        RedisConnection redisConnection = new RedisConnection(redisServer, redisPort);

        ff4j.setFeatureStore(new FeatureStoreRedis(redisConnection));
        ff4j.setPropertiesStore(new PropertyStoreRedis(redisConnection));
        ff4j.setEventRepository(new EventRepositoryRedis(redisConnection));

        return ff4j;
    }

}
