package com.saulloguilherme.ocr_api.service;

import com.saulloguilherme.ocr_api.dto.InvoiceResponseDTO;
import com.saulloguilherme.ocr_api.exception.NotFoundException;
import com.saulloguilherme.common.dto.InvoiceEventRequest;
import com.saulloguilherme.common.dto.InvoiceEventResponse;
import com.saulloguilherme.ocr_api.kafka.producer.OcrProducer;
import com.saulloguilherme.ocr_api.model.Invoice;
import com.saulloguilherme.ocr_api.model.OcrStatus;
import com.saulloguilherme.ocr_api.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private MinioService minioService;

    @Autowired
    private OcrProducer ocrProducer;

    @Transactional
    public ResponseEntity<InvoiceResponseDTO> processInvoice(MultipartFile file) {
        String path = minioService.save(file);

        Invoice invoice = new Invoice();
        invoice.setUuid(UUID.randomUUID());
        invoice.setStoragePath(path);
        invoice.setStatus(OcrStatus.PROCESSING);
        invoice.setCreatedAt(Timestamp.from(Instant.now()));
        this.save(invoice);

        InvoiceEventRequest request = new InvoiceEventRequest();
        request.setStoragePath(path);
        request.setUuid(invoice.getUuid());
        this.createEvent(request);

        InvoiceResponseDTO dto = new InvoiceResponseDTO(invoice.getUuid(), invoice.getCreatedAt(), invoice.getStatus());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
    }

    public ResponseEntity<InvoiceResponseDTO> getInvoice(UUID uuid) {

        Invoice invoice = invoiceRepository.findById(uuid).orElseThrow(
                () -> new NotFoundException("Não foi possível encontrar nenhuma nota fiscal com o identificador enviado."));

        InvoiceResponseDTO invoiceResponseDTO = new InvoiceResponseDTO();
        invoiceResponseDTO.createFromInvoice(invoice);

        return ResponseEntity.status(HttpStatus.OK).body(invoiceResponseDTO);
    }

    public void processEvent(InvoiceEventResponse eventResponse) {
        UUID uuid = eventResponse.getUuid();

        Invoice invoice = invoiceRepository.findById(uuid).orElseThrow(
                () -> new NotFoundException("Não foi possível encontrar nenhuma nota fiscal com o identificador enviado."));

        invoice.setStatus(OcrStatus.SUCCESS);
        invoice.setProducts(eventResponse.getProducts());
        invoice.setParsedText(eventResponse.getParsedText());
        invoice.setTotalValue(eventResponse.getTotalValue());
        invoice.setUpdatedAt(Timestamp.from(Instant.now()));

        this.save(invoice);
    }

    public void createEvent(InvoiceEventRequest eventRequest) {
        ocrProducer.sendInvoice(eventRequest);
    }

    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }


}
