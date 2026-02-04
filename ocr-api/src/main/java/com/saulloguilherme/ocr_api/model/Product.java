package com.saulloguilherme.ocr_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "invoice_uuid")
    private Invoice invoice;

    private String name;

    private BigDecimal value;

}
