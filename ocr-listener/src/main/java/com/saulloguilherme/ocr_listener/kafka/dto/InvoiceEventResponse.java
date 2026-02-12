package com.saulloguilherme.ocr_listener.kafka.dto;

import com.saulloguilherme.ocr_listener.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceEventResponse {

    private UUID uuid;
    private String parsedText;
    private List<ProductResponse> products;
    private BigDecimal totalValue;

}
