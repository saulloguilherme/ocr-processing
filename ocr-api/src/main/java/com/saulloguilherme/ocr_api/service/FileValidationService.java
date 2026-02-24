package com.saulloguilherme.ocr_api.service;

import com.saulloguilherme.ocr_api.exception.BadRequestException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileValidationService {

    private final int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MegaBytes

    public void validate(MultipartFile file) {
        validateSize(file);
        validateExtension(file);
    }

    private void validateContent(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("O arquivo está vazio.");
        }
    }

    private void validateSize(MultipartFile file) {
        if (file.getSize() > maxUploadSizeInMb) {
            throw new BadRequestException("Tamanho máximo excedido.");
        }
    }

    private void validateExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"png".equals(extension) && !"jpeg".equals(extension) && !"jpg".equals(extension)) {
            throw new BadRequestException("O tipo do arquivo não é permitido.");
        }
    }

}
