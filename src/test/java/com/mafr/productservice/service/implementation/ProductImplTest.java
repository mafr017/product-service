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
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductImplTest {
    @InjectMocks
    private ProductImpl productImpl;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
    }

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
    @Test
    void getByIDTest() {
        //arrange
        ProductEntity entity = new ProductEntity(1L, "Coca-cola", "Softdrink", 123, new BigDecimal("4500.00"));

        //act
        when(productRepository.findById(any())).thenReturn(Optional.of(entity));

        //assert
        ProductEntity actual = productImpl.getByID(any());
        Assertions.assertEquals(entity, actual);
    }
    @Test
    void saveTest() {
        //arrange
        ProductEntity entity = new ProductEntity(null, "Coca-cola", "Softdrink", 123, new BigDecimal("4500.00"));

        //act
        when(productRepository.save(any())).thenReturn(entity);

        //assert
        ProductEntity actual = productImpl.save(any());
        Assertions.assertEquals(entity, actual);
    }
    @Test
    void deleteTest() {
        //arrange

        //act
        doNothing().when(productRepository).delete(any());

        //assert
        productImpl.delete(any());
        productRepository.delete(any());
        verify(productRepository).delete(any());

    }
    @Test
    void updateTest() {
        //arrange
        ProductEntity entity = new ProductEntity(1L, "Coca-cola", null, 123, new BigDecimal("4500.00"));
        ProductEntity entityUpdateD = new ProductEntity(1L, "Sprite", "Softdrink", 123, new BigDecimal("4500.00"));

        //act
        when(productRepository.findById(any())).thenReturn(Optional.of(entity));
        when(productRepository.save(entityUpdateD)).thenReturn(entityUpdateD);

        //assert
        ProductEntity actual = productImpl.update(entityUpdateD);
        Assertions.assertEquals(entity, actual);
    }
    @Test
    void updateTestError() {
        //arrange
        ProductEntity entity = new ProductEntity(2L, "Coca-cola", null, 123, new BigDecimal("4500.00"));

        Assertions.assertThrows(NotFoundException.class, () -> {
            productImpl.update(entity);
        });
    }
}
