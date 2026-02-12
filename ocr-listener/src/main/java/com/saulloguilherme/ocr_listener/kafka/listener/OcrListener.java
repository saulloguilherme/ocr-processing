package com.saulloguilherme.ocr_listener.kafka.listener;

import com.saulloguilherme.ocr_listener.kafka.dto.InvoiceEventRequest;
import com.saulloguilherme.ocr_listener.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class OcrListener {

    @Autowired
    private InvoiceService invoiceService;

    @KafkaListener(topics = "${topic.ocr.requests}", containerFactory = "invoiceConsumerFactory")
    public void listenResult(InvoiceEventRequest eventRequest) {
        invoiceService.processEvent(eventRequest);
    }
}
