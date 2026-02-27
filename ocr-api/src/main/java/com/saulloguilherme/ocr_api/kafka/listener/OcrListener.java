package com.saulloguilherme.ocr_api.kafka.listener;

import com.saulloguilherme.common.dto.InvoiceEventRequest;
import com.saulloguilherme.common.dto.InvoiceEventResponse;
import com.saulloguilherme.ocr_api.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OcrListener {

    @Autowired
    private InvoiceService invoiceService;

    @KafkaListener(topics = "${topic.ocr.results}", groupId = "ocr", containerFactory = "invoiceEventResponseConcurrentKafkaListenerContainerFactory")
    public void listenResult(InvoiceEventResponse eventResponse) {
        invoiceService.processEvent(eventResponse);
    }

    @KafkaListener(topics = "${topic.ocr.requests.dlt}", groupId = "ocr")
    public void listenDlt(InvoiceEventRequest event) {
        invoiceService.processDlqEvent(event);
    }
}
