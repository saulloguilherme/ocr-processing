package com.saulloguilherme.ocr_listener.kafka.config;

import com.saulloguilherme.ocr_listener.kafka.dto.InvoiceEventRequest;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

        @Autowired
        private KafkaProperties kafkaProperties;

        @Bean
        public ConsumerFactory<String, String> consumerFactory() {
            Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
            return new DefaultKafkaConsumerFactory<>(properties);
        }

        @Bean
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }

        @Bean
        public ConsumerFactory<String, InvoiceEventRequest> invoiceConsumerFactory() {
            Map<String, Object> properties = kafkaProperties.buildConsumerProperties();
            return new DefaultKafkaConsumerFactory<>(
                    properties,
                    new StringDeserializer(),
                    new JacksonJsonDeserializer<>(InvoiceEventRequest.class));
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, InvoiceEventRequest>
        invoiceEventResponseConcurrentKafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, InvoiceEventRequest> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(invoiceConsumerFactory());
            return factory;
        }
}
