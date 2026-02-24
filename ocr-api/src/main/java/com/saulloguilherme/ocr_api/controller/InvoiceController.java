package com.saulloguilherme.ocr_api.controller;

import com.saulloguilherme.ocr_api.dto.InvoiceResponseDTO;
import com.saulloguilherme.ocr_api.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> sendFile(@Valid @RequestParam("file") MultipartFile file) {
        return invoiceService.processInvoice(file); // 202, 400
    }

    @GetMapping
    public ResponseEntity<InvoiceResponseDTO> getInvoice(@Valid @PathVariable UUID uuid) {
        return invoiceService.getInvoice(uuid); // 200, 404
    }


}
