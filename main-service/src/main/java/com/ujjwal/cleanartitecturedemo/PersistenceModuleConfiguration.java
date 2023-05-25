package com.ujjwal.cleanartitecturedemo;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.ujjwal.cleanartitecturedemo.domain.repository.OrderDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@ConfigurationPropertiesScan
@EnableReactiveMongoRepositories
@Slf4j
public class PersistenceModuleConfiguration {
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
        return new ReactiveMongoTemplate(mongoClient, "order-details");
    }

    @Bean
    public MongoClient mongoClient(PersistenceProperties properties) {
        log.info("MongoClient connection string: " + properties.mongoConnectionString());
        return MongoClients.create(properties.mongoConnectionString());
    }
}
