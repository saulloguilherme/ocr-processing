package com.saulloguilherme.ocr_api.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;

public class OcrListener {

    @KafkaListener(topics = "${topic.ocr.result}")
    public void listenResult(String message) {
        System.out.println("message received");
    }
}
