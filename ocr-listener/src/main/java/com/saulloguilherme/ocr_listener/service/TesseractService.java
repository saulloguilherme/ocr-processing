package com.saulloguilherme.ocr_listener.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Service
public class TesseractService {

    @Value("${TESSERACT_DATA_PATH}")
    private String tessDataPath;

    @Value("${TESSERACT_LANGUAGE}")
    private String language;

    public String extractText(InputStream imageStream) {
        try {
            BufferedImage image = ImageIO.read(imageStream);
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath(tessDataPath);
            tesseract.setLanguage(language);
            return tesseract.doOCR(image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
