package com.saulloguilherme.ocr_api.kafka.producer;

import com.saulloguilherme.common.dto.InvoiceEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OcrProducer {

    @Value("${topic.ocr.requests}")
    private String OcrRequestTopic;

    @Autowired
    KafkaTemplate<String, InvoiceEventRequest> kafkaTemplate;

    public void sendInvoice(InvoiceEventRequest eventRequest) {
        kafkaTemplate.send(OcrRequestTopic, eventRequest);
    }
}
