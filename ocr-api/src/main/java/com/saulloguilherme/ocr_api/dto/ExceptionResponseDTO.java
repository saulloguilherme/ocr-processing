package com.saulloguilherme.ocr_api.dto;

import java.time.Instant;

public record ExceptionResponseDTO (Instant timestamp, Integer status, String error) {

}
