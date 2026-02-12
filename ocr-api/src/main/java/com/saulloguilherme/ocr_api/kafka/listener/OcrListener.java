package com.saulloguilherme.ocr_api.kafka.listener;

import com.saulloguilherme.ocr_api.kafka.dto.InvoiceEventResponse;
import com.saulloguilherme.ocr_api.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class OcrListener {

    @Autowired
    private InvoiceService invoiceService;

    @KafkaListener(topics = "${topic.ocr.results}", containerFactory = "invoiceConsumerFactory")
    public void listenResult(InvoiceEventResponse eventResponse) {
        invoiceService.processEvent(eventResponse);
    }
}
