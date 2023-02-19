package com.mafr.productservice.controller;

import com.mafr.productservice.entity.ProductEntity;
import com.mafr.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    @Test
    void getById() throws Exception {

        ProductEntity productEntity = new ProductEntity(1L, "Coca-cola", "Softdrink", 123, new BigDecimal("4500.00"));

        when(productService.getByID(1L)).thenReturn(productEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\r\n      \"id\": 1,\r\n      \"name\": \"Coca-cola\",\r\n      \"description\": \"Softdrink\",\r\n      \"stock\": 123,\r\n      \"price\": 4500.00\r\n    },\r\n  \"status\": 200,\r\n  \"message\": \"OK\"\r\n}"));
    }
    @Test
    void getByIdError() throws Exception {
        when(productService.getByID(anyLong())).thenThrow(new NoSuchElementException("No value present"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json("{\n  \"data\": \"No value present\",\n  \"status\": 400,\n  \"message\": \"Bad Request\"\n}"));
    }
    @Test
    public void create() throws Exception {
        String reqJson = "{\"name\": \"Sprite\", \"stock\": 120,  \"price\": 5000}";

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("Sprite");
        productEntity.setStock(120);
        productEntity.setPrice(new BigDecimal(5000));
        ProductEntity productEntity1 = new ProductEntity(1L, "Sprite", null, 120, new BigDecimal("5000.00"));

        when(productService.save(productEntity)).thenReturn(productEntity1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json("{\n  \"data\": {\n    \"id\": 1,\n    \"name\": \"Sprite\",\n    \"description\": null,\n    \"stock\": 120,\n    \"price\": 5000\n  },\n  \"status\": 200,\n  \"message\": \"OK\"\n}"));
    }
    @Test
    void deleteById() throws Exception {
        doAnswer((i) -> null).when(productService).delete(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/product/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json("{\n  \"data\": \"Success delete product with ID 1\",\n  \"status\": 200,\n  \"message\": \"OK\"\n}"));
    }
    @Test
    void deleteByIdError() throws Exception {
        doThrow(new EmptyResultDataAccessException("No class com.mafr.productservice.entity.ProductEntity entity with id 4 exists", 1)).when(productService).delete(any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/product/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json("{\n  \"data\": \"No class com.mafr.productservice.entity.ProductEntity entity with id 4 exists\",\n  \"status\": 400,\n  \"message\": \"Bad Request\"\n}"));
    }
    @Test
    public void update() throws Exception {
        String reqJson = "{\"id\": 1, \"name\": \"Ubi\", \"stock\": 10,  \"price\": 2000}";

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Ubi");
        productEntity.setStock(10);
        productEntity.setPrice(new BigDecimal(2000));
        ProductEntity productEntity1 = new ProductEntity(1L, "Ubi", null, 10, new BigDecimal("2000.00"));

        when(productService.update(productEntity)).thenReturn(productEntity1);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json("{\n  \"data\": {\n    \"id\": 1,\n    \"name\": \"Ubi\",\n    \"description\": null,\n    \"stock\": 10,\n    \"price\": 2000\n  },\n  \"status\": 200,\n  \"message\": \"OK\"\n}"));
    }
    @Test
    public void updateError() throws Exception {
        String reqJson = "{\"id\": 1, \"name\": \"Ubi\", \"stock\": 10,  \"price\": 2000}";

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Ubi");
        productEntity.setStock(10);
        productEntity.setPrice(new BigDecimal(2000));

        when(productService.update(productEntity)).thenThrow(new NotFoundException("Product not exist..."));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json("{\n  \"data\": \"Product not exist...\",\n  \"status\": 400,\n  \"message\": \"Bad Request\"\n}"));
    }
}
