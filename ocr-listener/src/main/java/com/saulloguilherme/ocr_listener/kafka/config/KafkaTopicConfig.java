package com.saulloguilherme.ocr_listener.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${topic.ocr.requests}")
    private String ocrRequests;

    @Value("${topic.ocr.results}")
    private String ocrResults;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(config);
    }

    @Bean
    public NewTopic ocrRequests() {
        return new NewTopic(ocrRequests, 1, (short) 1);
    }

    @Bean
    public NewTopic ocrResults() {
        return new NewTopic(ocrResults, 1, (short) 1);
    }

}
