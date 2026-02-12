package com.saulloguilherme.ocr_api.service;

import com.saulloguilherme.ocr_api.kafka.dto.InvoiceEventRequest;
import com.saulloguilherme.ocr_api.kafka.dto.InvoiceEventResponse;
import com.saulloguilherme.ocr_api.kafka.producer.OcrProducer;
import com.saulloguilherme.ocr_api.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private MinioService minioService;

    @Autowired
    private OcrProducer ocrProducer;

    public ResponseEntity<> processInvoice(MultipartFile file) {
        try {
            String path = minioService.save(file); // minio error
            InvoiceEventRequest request = new InvoiceEventRequest();
            request.setStoragePath(path);
            createEvent(request);
        }
    }

    public void getStatus(UUID uuid) {

    }

    public void processEvent(InvoiceEventResponse eventResponse) {

    }

    public void createEvent(InvoiceEventRequest eventRequest) {
        ocrProducer.sendInvoice(eventRequest);
    }


}
