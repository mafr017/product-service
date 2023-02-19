package com.mafr.productservice.controller;

import com.mafr.productservice.dto.ResponseDTO;
import com.mafr.productservice.entity.ProductEntity;
import com.mafr.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ProductEntity>>> getAll() {
        ResponseDTO<List<ProductEntity>> response = new ResponseDTO<>(HttpStatus.OK, service.getAll());
        log.info("Success get products");
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<?>> getByID(@PathVariable Long id) {
        try {
            ResponseDTO<ProductEntity> response = new ResponseDTO<>(HttpStatus.OK, service.getByID(id));
            log.info("Success get product with ID {}", id);
            return new ResponseEntity<>(response, response.getHttpStatus());
        } catch (Exception e) {
            log.error("Failed get product with ID {}", id);
            ResponseDTO<?> errRes = new ResponseDTO<>(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(errRes, errRes.getHttpStatus());
        }
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<?>> save(@Valid @RequestBody ProductEntity param) {
        ResponseDTO<ProductEntity> response = new ResponseDTO<>(HttpStatus.OK, service.save(param));
        log.info("Success save product {}", param);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<?>> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            log.info("Success delete product with ID {}", id);
            ResponseDTO<?> response = new ResponseDTO<>(HttpStatus.OK, "Success delete product with ID "+id);
            return new ResponseEntity<>(response, response.getHttpStatus());
        } catch (Exception e) {
            log.error("Failed delete product with ID {}", id);
            ResponseDTO<?> errRes = new ResponseDTO<>(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(errRes, errRes.getHttpStatus());
        }
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<?>> update(@Valid @RequestBody ProductEntity param) {
        try {
            ResponseDTO<ProductEntity> response = new ResponseDTO<>(HttpStatus.OK, service.update(param));
            log.info("Success update product {}", param);
            return new ResponseEntity<>(response, response.getHttpStatus());
        } catch (Exception e) {
            log.error("Failed update product {}", param);
            ResponseDTO<?> errRes = new ResponseDTO<>(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(errRes, errRes.getHttpStatus());
        }
    }
}
