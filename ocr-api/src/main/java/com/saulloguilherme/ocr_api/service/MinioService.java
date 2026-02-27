package com.saulloguilherme.ocr_api.service;

import com.saulloguilherme.ocr_api.exception.BadRequestException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class MinioService {

    @Value("${MINIO_BUCKET}")
    private String bucket;

    @Autowired
    private FileValidationService fileValidationService;

    @Autowired
    private MinioClient minioClient;

    public String save(MultipartFile file) {
        fileValidationService.validate(file);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = UUID.randomUUID() + "." + extension;

        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucket)
                            .object(name)
                            .stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            throw new BadRequestException("Falha ao salvar arquivo no MinIO");
        }
        return name;
    }

}
