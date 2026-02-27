package com.saulloguilherme.ocr_listener.kafka.listener;

import com.saulloguilherme.common.dto.InvoiceEventRequest;
import com.saulloguilherme.ocr_listener.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OcrListener {

    @Autowired
    private InvoiceService invoiceService;

    @KafkaListener(topics = "${topic.ocr.requests}", groupId = "ocr", containerFactory = "invoiceEventResponseConcurrentKafkaListenerContainerFactory")
    public void listenResult(InvoiceEventRequest eventRequest) {
        invoiceService.processEvent(eventRequest);
    }
}
