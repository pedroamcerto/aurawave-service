package com.aurawave.controller;

import com.aurawave.dto.sessionDto.SessionRequestDto;
import com.aurawave.dto.sessionDto.SessionResponseDto;
import com.aurawave.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Sessões", description = "CRUD de Sessões")
public class SessionController {

    @Autowired
    private SessionService service;

    @Operation(summary = "Cria uma sessão")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SessionResponseDto> create(@Valid @RequestBody SessionRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Atualiza uma sessão")
    @PutMapping("/{id}")
    public ResponseEntity<SessionResponseDto> update(@PathVariable Long id, @Valid @RequestBody SessionRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Obtem uma sessão por ID")
    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Obtem uma lista de sessões")
    @GetMapping
    public ResponseEntity<List<SessionResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Deleta uma sessão")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
