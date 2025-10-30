package com.aurawave.controller;

import com.aurawave.dto.batchDto.BatchRequestDto;
import com.aurawave.dto.batchDto.BatchResponseDto;
import com.aurawave.service.BatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
@Tag(name = "Lotes", description = "CRUD de Lotes")
public class BatchController {

    @Autowired
    private BatchService service;

    @Operation(summary = "Cria um lote")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BatchResponseDto> create(@Valid @RequestBody BatchRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Atualiza um lote")
    @PutMapping("/{id}")
    public ResponseEntity<BatchResponseDto> update(@PathVariable Long id, @Valid @RequestBody BatchRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Obtem um lote por ID")
    @GetMapping("/{id}")
    public ResponseEntity<BatchResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Obtem uma lista de lotes")
    @GetMapping
    public ResponseEntity<List<BatchResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Deleta um lote")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
