package com.aurawave.controller;

import com.aurawave.domain.enumerated.ProductStatus;
import com.aurawave.dto.productDto.ProductRequestDto;
import com.aurawave.dto.productDto.ProductResponseDto;
import com.aurawave.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean ProductService service;

    private ProductRequestDto req() {
        return new ProductRequestDto(
                "Mouse",
                LocalDateTime.of(2030,1,1,12,0),
                10L,
                new BigDecimal("20.00"),
                ProductStatus.AVAILABLE
        );
    }

    private ProductResponseDto resp(long id, String name) {
        return new ProductResponseDto(
                id,
                name,
                LocalDateTime.of(2030,1,1,12,0),
                10L,
                new BigDecimal("20.00"),
                ProductStatus.AVAILABLE,
                LocalDateTime.of(2025,1,1,10,0),
                LocalDateTime.of(2025,1,2,10,0)
        );
    }

    @Test
    void testCreateShouldReturn201AndBody() throws Exception {
        var dto = req();
        var out = resp(1L, "Mouse");
        when(service.create(any(ProductRequestDto.class))).thenReturn(out);

        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Mouse"));

        verify(service).create(any(ProductRequestDto.class));
    }

    @Test
    void testUpdateShouldReturn200AndBody() throws Exception {
        var dto = req();
        var out = resp(7L, "Keyboard");
        when(service.update(eq(7L), any(ProductRequestDto.class))).thenReturn(out);

        mvc.perform(put("/api/products/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("Keyboard"));

        verify(service).update(eq(7L), any(ProductRequestDto.class));
    }

    @Test
    void testGetByIdShouldReturn200AndBody() throws Exception {
        var out = resp(3L, "Headset");
        when(service.getById(3L)).thenReturn(out);

        mvc.perform(get("/api/products/{id}", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Headset"));

        verify(service).getById(3L);
    }

    @Test
    void testGetAllShouldReturn200AndList() throws Exception {
        var r1 = resp(1L, "A");
        var r2 = resp(2L, "B");
        when(service.getAll()).thenReturn(List.of(r1, r2));

        mvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(service).getAll();
    }

    @Test
    void testDeleteShouldReturn204() throws Exception {
        mvc.perform(delete("/api/products/{id}", 9))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).delete(9L);
    }
}
