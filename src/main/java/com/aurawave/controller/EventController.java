package com.aurawave.controller;

import com.aurawave.dto.eventDto.EventRequestDto;
import com.aurawave.dto.eventDto.EventResponseDto;
import com.aurawave.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Eventos", description = "CRUD de Eventos")
public class EventController {

    @Autowired
    private EventService service;

    @Operation(summary = "Cria um evento")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventResponseDto> create(@Valid @RequestBody EventRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Atualiza um evento")
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> update(@PathVariable Long id, @Valid @RequestBody EventRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Obtem um evento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Obtem uma lista de eventos")
    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Deleta um evento")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
