package com.aurawave.controller;

import com.aurawave.dto.productDto.ProductRequestDto;
import com.aurawave.dto.productDto.ProductResponseDto;
import com.aurawave.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @Valid @RequestBody ProductRequestDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ProductResponseDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}