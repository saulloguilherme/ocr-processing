package com.saulloguilherme.ocr_api.kafka.dto;

import com.saulloguilherme.ocr_api.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEventResponse {

    private UUID uuid;
    private String parsedText;
    private List<ProductResponse> products;
    private BigDecimal totalValue;

}

