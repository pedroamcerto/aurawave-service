package com.aurawave.controller;

import com.aurawave.dto.manufacturerDto.ManufacturerRequestDto;
import com.aurawave.dto.manufacturerDto.ManufacturerResponseDto;
import com.aurawave.service.ManufacturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
@Tag(name = "Fabricantes", description = "CRUD de Fabricantes")
public class ManufacturerController {

    @Autowired
    private ManufacturerService service;

    @Operation(summary = "Cria um fabricante")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ManufacturerResponseDto> create(@Valid @RequestBody ManufacturerRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Atualiza um fabricante")
    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDto> update(@PathVariable Long id, @Valid @RequestBody ManufacturerRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Obtem um fabricante por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Obtem uma lista de fabricantes")
    @GetMapping
    public ResponseEntity<List<ManufacturerResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Deleta um fabricante")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
