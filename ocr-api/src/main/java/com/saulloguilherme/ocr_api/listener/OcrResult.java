package com.saulloguilherme.ocr_api.listener;

import org.springframework.kafka.annotation.KafkaListener;

public class OcrResult {

    @KafkaListener(topics = "${topic.ocr.result}")
    public void listenResult(String message) {
        System.out.println("message received");
    }
}
