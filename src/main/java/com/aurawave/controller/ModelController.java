package com.aurawave.controller;

import com.aurawave.dto.modelDto.ModelRequestDto;
import com.aurawave.dto.modelDto.ModelResponseDto;
import com.aurawave.service.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
@Tag(name = "Modelos", description = "CRUD de Modelos")
public class ModelController {

    @Autowired
    private ModelService service;

    @Operation(summary = "Cria um modelo")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ModelResponseDto> create(@Valid @RequestBody ModelRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Atualiza um modelo")
    @PutMapping("/{id}")
    public ResponseEntity<ModelResponseDto> update(@PathVariable Long id, @Valid @RequestBody ModelRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Obtem um modelo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ModelResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Obtem uma lista de modelos")
    @GetMapping
    public ResponseEntity<List<ModelResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Deleta um modelo")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
