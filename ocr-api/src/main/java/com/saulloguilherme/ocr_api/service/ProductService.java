package com.saulloguilherme.ocr_api.service;

import com.saulloguilherme.ocr_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository  productRepository;


}
