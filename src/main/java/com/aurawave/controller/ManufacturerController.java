package com.aurawave.controller;

import com.aurawave.dto.PageResponseDto;
import com.aurawave.dto.manufacturerDto.ManufacturerRequestDto;
import com.aurawave.dto.manufacturerDto.ManufacturerResponseDto;
import com.aurawave.service.ManufacturerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerService service;

    @PostMapping("/add")
    public ResponseEntity<ManufacturerResponseDto> create(@Valid @RequestBody ManufacturerRequestDto request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebida requisição para criar fabricante.");
        ManufacturerResponseDto manufacturerResponseDto = service.create(request);
        return ResponseEntity.created(uriComponentsBuilder.path("/v1/manufacturers/add/{id}")
                        .buildAndExpand(manufacturerResponseDto.id())
                        .toUri())
                .body(manufacturerResponseDto);
    }

    @GetMapping("/")
    public ResponseEntity<PageResponseDto<ManufacturerResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Listando fabricantes: page={}, size={}", page, size);
        return ResponseEntity.ok(service.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDto> update(@PathVariable UUID id,
                                                          @Valid @RequestBody ManufacturerRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
