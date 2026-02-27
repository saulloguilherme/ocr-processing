package com.saulloguilherme.ocr_listener.kafka.producer;

import com.saulloguilherme.common.dto.InvoiceEventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OcrProducer {

    @Value("${topic.ocr.results}")
    private String OcrRequestTopic;

    @Autowired
    KafkaTemplate<String, InvoiceEventResponse> kafkaTemplate;

    public void sendInvoice(InvoiceEventResponse eventResponse) {
        kafkaTemplate.send(OcrRequestTopic, eventResponse);
    }
}
