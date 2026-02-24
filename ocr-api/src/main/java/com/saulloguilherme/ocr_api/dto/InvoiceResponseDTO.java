package com.saulloguilherme.ocr_api.dto;

import com.saulloguilherme.ocr_api.model.Invoice;
import com.saulloguilherme.ocr_api.model.OcrStatus;
import com.saulloguilherme.ocr_api.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponseDTO  {

    private UUID uuid;
    private Timestamp createdAt;
    private OcrStatus status;
    private String parsedText;
    private List<Product> products;
    private BigDecimal totalValue;
    private Timestamp updatedAt;
    private String imageUrl;

    public InvoiceResponseDTO(UUID uuid, @NotNull Timestamp createdAt, OcrStatus status) {
        this.uuid = uuid;
        this.createdAt = createdAt;
        this.status = status;
    }

    public void createFromInvoice(Invoice invoice) {
        this.uuid = invoice.getUuid();
        this.createdAt = invoice.getCreatedAt();
        this.status = invoice.getStatus();
        this.parsedText = invoice.getParsedText();
        this.products = invoice.getProducts();
        this.totalValue = invoice.getTotalValue();
        this.updatedAt = invoice.getUpdatedAt();
    }
}
