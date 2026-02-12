package com.saulloguilherme.ocr_api.service;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class MinioService {

    @Autowired
    private MinioClient minioClient;

    public String save(MultipartFile file) {
        minioClient.;
    }

}
