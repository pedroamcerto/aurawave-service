package com.aurawave.controller;

import com.aurawave.dto.PageResponseDto;
import com.aurawave.dto.supplierDto.SupplierRequestDto;
import com.aurawave.dto.supplierDto.SupplierResponseDto;
import com.aurawave.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping("/add")
    public ResponseEntity<SupplierResponseDto> create(@Valid @RequestBody SupplierRequestDto supplierRequestDto, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Inserindo uma nova Empresa: {}.", supplierRequestDto.name());
        SupplierResponseDto supplierResponseDto = supplierService.create(supplierRequestDto);
        return ResponseEntity.created(uriComponentsBuilder.path("/v1/supplier/add/{id}")
                        .buildAndExpand(supplierResponseDto.getId())
                        .toUri())
                .body(supplierResponseDto);
    }

    @GetMapping("/")
    public ResponseEntity<PageResponseDto<SupplierResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Buscando fornecedores (page={}, size={})", page, size);
        return ResponseEntity.ok(supplierService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> findById(@PathVariable UUID id) {
        log.info("Buscando fornecedor por Id: {}.", id);
        return ResponseEntity.ok(supplierService.findByIdToDto(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(supplierService.count());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> update(
            @PathVariable UUID id,
            @Valid  @RequestBody SupplierRequestDto request) {
        log.info("Atualizando fornecedor com Id: {}.", id);
        return ResponseEntity.ok(supplierService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.info("Deletando fornecedor com Id: {}.", id);
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
