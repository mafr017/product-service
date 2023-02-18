package com.mafr.productservice.service;

import com.mafr.productservice.dto.ResponseDTO;
import com.mafr.productservice.entity.ProductEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<ProductEntity> getAll();

    ProductEntity getByID(Long id);

    ProductEntity save(ProductEntity param);

    void delete(Long id);

    ProductEntity update(ProductEntity param);
}
