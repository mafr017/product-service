package com.mafr.productservice.service.implementation;

import com.mafr.productservice.entity.ProductEntity;
import com.mafr.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductImplTest {
    @InjectMocks
    private ProductImpl productImpl;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {}

    @Test
    void getAllTest() {
        //arrange
        ProductEntity entity = new ProductEntity(1L, "Coca-cola", "Softdrink", 123, new BigDecimal("4500.00"));
        ProductEntity entity2 = new ProductEntity(2L, "Chitato", "Snack", 110, new BigDecimal("5000.00"));
        List<ProductEntity> expected = List.of(entity, entity2);

        //act
        when(productRepository.findAll()).thenReturn(expected);

        //assert
        List<ProductEntity> actual = productImpl.getAll();
        Assertions.assertEquals(expected, actual);
    }
}
