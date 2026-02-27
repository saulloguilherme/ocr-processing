package com.saulloguilherme.ocr_listener.service;

import com.saulloguilherme.common.dto.InvoiceEventRequest;
import com.saulloguilherme.common.dto.InvoiceEventResponse;
import com.saulloguilherme.ocr_listener.kafka.producer.OcrProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InvoiceService {

    @Autowired
    private MinioService minioService;

    @Autowired
    private TesseractService tesseractService;

    @Autowired
    OcrProducer ocrProducer;

    public void processEvent(InvoiceEventRequest eventRequest) {
        InvoiceEventResponse eventResponse = new InvoiceEventResponse(eventRequest.getUuid());

        InputStream image = minioService.getImage(eventRequest.getStoragePath());

        String parsedText = tesseractService.extractText(image);

        BigDecimal total = this.extractTotal(parsedText);

        eventResponse.setParsedText(parsedText);
        eventResponse.setTotalValue(total);

        this.produceEvent(eventResponse);
    }

    public void produceEvent(InvoiceEventResponse eventResponse) {
        ocrProducer.sendInvoice(eventResponse);
    }

    private BigDecimal extractTotal(String text) {
        Pattern pattern = Pattern.compile("VALOR\\s+(?:TOTAL|A\\s+PAGAR)\\s+R\\$\\s*([0-9,]+)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String value = matcher.group(1).replace(",", ".");
            return new BigDecimal(value);
        }
        return null;
    }
}
