package com.aurawave.controller;

import com.aurawave.dto.warehouseDto.WarehouseRequestDto;
import com.aurawave.dto.warehouseDto.WarehouseResponseDto;
import com.aurawave.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarehouseResponseDto create(@Valid @RequestBody WarehouseRequestDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public WarehouseResponseDto update(@PathVariable Long id, @Valid @RequestBody WarehouseRequestDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public WarehouseResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<WarehouseResponseDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

