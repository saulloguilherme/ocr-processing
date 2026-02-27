package com.saulloguilherme.ocr_listener.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

@Service
public class TesseractService {

    @Value("${TESSERACT_DATA_PATH}")
    private String tessDataPath;

    @Value("${TESSERACT_LANGUAGE}")
    private String language;

    public String extractText(InputStream imageStream) {
        try {
            BufferedImage original = ImageIO.read(imageStream);

            BufferedImage gray = new BufferedImage(
                    original.getWidth(),
                    original.getHeight(),
                    BufferedImage.TYPE_BYTE_GRAY
            );

            gray.getGraphics().drawImage(original, 0, 0, null);

            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath(tessDataPath);
            tesseract.setLanguage(language);

            tesseract.setTessVariable("tessedit_char_whitelist",
                    "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,:-/ R$");

            tesseract.setPageSegMode(6);


            return tesseract.doOCR(gray);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
