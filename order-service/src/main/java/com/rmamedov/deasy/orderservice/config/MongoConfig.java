package com.rmamedov.deasy.orderservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableMongoAuditing
@EnableReactiveMongoRepositories(basePackages = "com.rmamedov.deasy.orderservice.repository")
public class MongoConfig {
}
