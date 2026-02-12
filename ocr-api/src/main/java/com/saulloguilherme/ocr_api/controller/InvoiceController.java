package com.saulloguilherme.ocr_api.controller;

import com.saulloguilherme.ocr_api.kafka.dto.InvoiceEventResponse;
import com.saulloguilherme.ocr_api.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<String> sendFile(@Valid @RequestParam("file") MultipartFile file) {
        return invoiceService.processInvoice(file); // 202, 400
    }

    @GetMapping
    public ResponseEntity<Optional<InvoiceEventResponse>> verifyProcessing() {

    }


}
