package com.aurawave.controller;

import com.aurawave.dto.collaboratorDto.CollaboratorRequestDto;
import com.aurawave.dto.collaboratorDto.CollaboratorResponseDto;
import com.aurawave.service.CollaboratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collaborators")
@Tag(name = "Colaboradores", description = "CRUD de Colaboradores")
public class CollaboratorController {

    @Autowired
    private CollaboratorService service;

    @Operation(summary = "Cria um colaborador")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CollaboratorResponseDto> create(@Valid @RequestBody CollaboratorRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Atualiza um colaborador")
    @PutMapping("/{id}")
    public ResponseEntity<CollaboratorResponseDto> update(@PathVariable Long id, @Valid @RequestBody CollaboratorRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Obtem um colaborador por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CollaboratorResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Obtem uma lista de colaboradores")
    @GetMapping
    public ResponseEntity<List<CollaboratorResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Deleta um colaborador")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
