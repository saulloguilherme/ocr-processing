package com.saulloguilherme.ocr_api.kafka.producer;

import com.saulloguilherme.ocr_api.kafka.dto.InvoiceEventRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

public class OcrProducer {

    @Value("${topic.ocr.requests}")
    private String OcrRequestTopic;

    @Autowired
    KafkaTemplate<String, InvoiceEventRequest> kafkaTemplate;

    public void sendInvoice(InvoiceEventRequest eventRequest) {
        kafkaTemplate.send(OcrRequestTopic, eventRequest);
    }
}
