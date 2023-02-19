package com.mafr.productservice.controller;

import com.mafr.productservice.entity.ProductEntity;
import com.mafr.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;
    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(productController).build();
    }
    @Test
    void getAll() throws Exception {
        List<ProductEntity> expected = new ArrayList<>(List.of(
                new ProductEntity(1L, "Coca-cola", "Softdrink", 123, new BigDecimal("4500.00")),
                new ProductEntity(2L, "Chitato", "Snack", 110, new BigDecimal("5000.00"))
        ));

        when(productService.getAll()).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": [\r\n    {\r\n      \"id\": 1,\r\n      \"name\": \"Coca-cola\",\r\n      \"description\": \"Softdrink\",\r\n      \"stock\": 123,\r\n      \"price\": 4500.00\r\n    },\r\n    {\r\n      \"id\": 2,\r\n      \"name\": \"Chitato\",\r\n      \"description\": \"Snack\",\r\n      \"stock\": 110,\r\n      \"price\": 5000.00\r\n    }\r\n  ],\r\n  \"status\": 200,\r\n  \"message\": \"OK\"\r\n}"));
    }
}
