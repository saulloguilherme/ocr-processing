package com.saulloguilherme.ocr_listener.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${MINIO_BUCKET}")
    private String bucketName;

    public InputStream getImage(String objectKey) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
