package com.aurawave.controller;

import com.aurawave.dto.supplierDto.SupplierRequestDto;
import com.aurawave.dto.supplierDto.SupplierResponseDto;
import com.aurawave.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Fornecedores", description = "CRUD de Fornecedores")
public class SupplierController {

    @Autowired
    private SupplierService service;

    @Operation(summary = "Cria um fornecedor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SupplierResponseDto> create(@Valid @RequestBody SupplierRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Atualiza um fornecedor")
    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> update(@PathVariable Long id, @Valid @RequestBody SupplierRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Obtem um fornecedor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Obtem uma lista de fornecedores")
    @GetMapping
    public ResponseEntity<List<SupplierResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Deleta um fornecedor")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
