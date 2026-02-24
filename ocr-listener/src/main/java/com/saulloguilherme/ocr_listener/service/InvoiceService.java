package com.saulloguilherme.ocr_listener.service;

import com.saulloguilherme.ocr_listener.kafka.dto.InvoiceEventRequest;
import com.saulloguilherme.ocr_listener.kafka.dto.InvoiceEventResponse;
import com.saulloguilherme.ocr_listener.kafka.producer.OcrProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class InvoiceService {

    @Autowired
    private MinioService minioService;

    @Autowired
    private TesseractService tesseractService;

    @Autowired
    OcrProducer ocrProducer;

    public void processEvent(InvoiceEventRequest eventRequest) {
        InvoiceEventResponse eventResponse = new InvoiceEventResponse(eventRequest.getUuid());

        InputStream image = minioService.getImage(eventRequest.getStoragePath());

        String parsedText = tesseractService.extractText(image);

        eventResponse.setParsedText(parsedText);

        this.produceEvent(eventResponse);

        // TODO total
        // TODO produtos
    }

    public void produceEvent(InvoiceEventResponse eventResponse) {
        ocrProducer.sendInvoice(eventResponse);
    }
}
