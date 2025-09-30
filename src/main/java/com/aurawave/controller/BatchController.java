package com.aurawave.controller;

import com.aurawave.dto.PageResponseDto;
import com.aurawave.dto.batchDto.BatchRequestDto;
import com.aurawave.dto.batchDto.BatchResponseDto;
import com.aurawave.service.BatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/batches")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @PostMapping("/add")
    public ResponseEntity<BatchResponseDto> create(@Valid @RequestBody BatchRequestDto request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebida requisição para criar lote.");
        BatchResponseDto batchResponseDto = batchService.create(request);
        return ResponseEntity.created(uriComponentsBuilder.path("/v1/batches/add/{id}")
                        .buildAndExpand(batchResponseDto.getId())
                        .toUri())
                .body(batchResponseDto);
    }

    @GetMapping("/")
    public ResponseEntity<PageResponseDto<BatchResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Listando lotes: page={}, size={}", page, size);
        return ResponseEntity.ok(batchService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(batchService.findById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(batchService.count());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BatchResponseDto> update(@PathVariable UUID id,
                                                   @Valid @RequestBody BatchRequestDto dto) {
        return ResponseEntity.ok(batchService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        batchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
