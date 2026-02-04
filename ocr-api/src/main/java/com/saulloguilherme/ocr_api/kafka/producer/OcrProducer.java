package com.saulloguilherme.ocr_api.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class OcrProducer {

    @Value("${topic.ocr.requests}")
    private String OcrRequestTopic;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendInvoice(String message) {
        kafkaTemplate.send(OcrRequestTopic, message);
    }
}
