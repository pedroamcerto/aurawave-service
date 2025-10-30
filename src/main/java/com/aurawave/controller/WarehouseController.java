package com.aurawave.controller;

import com.aurawave.dto.warehouseDto.WarehouseRequestDto;
import com.aurawave.dto.warehouseDto.WarehouseResponseDto;
import com.aurawave.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@Tag(name = "Almoxarifados", description = "CRUD de Almoxarifados")
public class WarehouseController {

    @Autowired
    private WarehouseService service;

    @Operation(summary = "Cria um almoxarifado")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WarehouseResponseDto> create(@Valid @RequestBody WarehouseRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Atualiza um almoxarifado")
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponseDto> update(@PathVariable Long id, @Valid @RequestBody WarehouseRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Obtem um almoxarifado por ID")
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Obtem uma lista de almoxarifados")
    @GetMapping
    public ResponseEntity<List<WarehouseResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Deleta um almoxarifado")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

