package com.aurawave.controller;

import com.aurawave.dto.itemDto.ItemRequestDto;
import com.aurawave.dto.itemDto.ItemResponseDto;
import com.aurawave.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@Tag(name = "Itens", description = "CRUD de Itens")
public class ItemController {

    @Autowired
    private ItemService service;

    @Operation(summary = "Cria um item")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ItemResponseDto> create(@Valid @RequestBody ItemRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Atualiza um item")
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDto> update(@PathVariable Long id, @Valid @RequestBody ItemRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Obtem um item por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Obtem uma lista de itens")
    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Deleta um item")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
