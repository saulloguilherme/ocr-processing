package com.saulloguilherme.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceEventRequest {

    private UUID uuid;
    private String storagePath;

}
