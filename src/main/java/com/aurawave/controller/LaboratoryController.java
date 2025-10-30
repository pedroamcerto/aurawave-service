package com.aurawave.controller;

import com.aurawave.dto.laboratoryDto.LaboratoryRequestDto;
import com.aurawave.dto.laboratoryDto.LaboratoryResponseDto;
import com.aurawave.service.LaboratoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laboratories")
@Tag(name = "Laboratórios", description = "CRUD de Laboratórios")
public class LaboratoryController {

    @Autowired
    private LaboratoryService service;

    @Operation(summary = "Cria um laboratório")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LaboratoryResponseDto> create(@Valid @RequestBody LaboratoryRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Atualiza um laboratório")
    @PutMapping("/{id}")
    public ResponseEntity<LaboratoryResponseDto> update(@PathVariable Long id, @Valid @RequestBody LaboratoryRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Obtem um laboratório por ID")
    @GetMapping("/{id}")
    public ResponseEntity<LaboratoryResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Obtem uma lista de laboratórios")
    @GetMapping
    public ResponseEntity<List<LaboratoryResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Deleta um laboratório")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
