package com.aurawave.service;

import com.aurawave.core.domain.Batch;
import com.aurawave.core.domain.Supplier;
import com.aurawave.core.exception.ConflictException;
import com.aurawave.core.exception.NotFoundException;
import com.aurawave.dto.PageResponseDto;
import com.aurawave.dto.batchDto.BatchRequestDto;
import com.aurawave.dto.batchDto.BatchResponseDto;
import com.aurawave.repository.BatchRepository;
import com.aurawave.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepository repository;
    private final SupplierService supplierService;
    private final ModelMapper modelMapper;

    @Transactional
    public BatchResponseDto create(BatchRequestDto request) {
        log.info("Criando novo lote: name={}, expirationDate={}",
                request.name(), request.expirationDate());

        Supplier supplier = supplierService.findById(request.supplierId());

        if (repository.existsByNameAndSupplier_Id(request.name(), supplier.getId())) {
            throw new ConflictException("Já existe um lote com este nome para o fornecedor informado.");
        }

        Batch batch = modelMapper.map(request, Batch.class);
        batch.setSupplier(supplier);

        repository.save(batch);
        log.info("Lote criado com sucesso: {} - {}", batch.getId(), batch.getName());

        return ModelMapperUtil.convertEntityToDto(batch, BatchResponseDto.class);
    }

    public PageResponseDto<BatchResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BatchResponseDto> pageDto = repository.findAll(pageable)
                .map(batch -> {
                    BatchResponseDto dto = modelMapper.map(batch, BatchResponseDto.class);
                    if (batch.getSupplier() != null) {
                        dto.setSupplierId(batch.getSupplier().getId());
                    }
                    return dto;
                });

        if (pageDto.isEmpty()) {
            throw new NotFoundException("Nenhum lote foi encontrado.");
        }
        return PageResponseDto.of(pageDto);
    }

    public BatchResponseDto findById(UUID id) {
        log.info("Buscando lote por Id: {}", id);
        Batch batch = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Batch %s not found".formatted(id)));
        BatchResponseDto dto = modelMapper.map(batch, BatchResponseDto.class);
        if (batch.getSupplier() != null) {
            dto.setSupplierId(batch.getSupplier().getId());
        }
        return dto;
    }

    public Long count() {
        log.info("Buscando a quantidade de lotes.");
        Long count = repository.count();
        log.info("Quantidade de lotes: {}", count);
        return count;
    }

    @Transactional
    public BatchResponseDto update(UUID id, BatchRequestDto request) {
        log.info("Atualizando lote Id: {}", id);

        Batch entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Batch %s not found".formatted(id)));

        Supplier supplier = supplierService.findById(request.supplierId());

        if (repository.existsByNameAndSupplier_IdAndIdNot(request.name(), supplier.getId(), id)) {
            throw new ConflictException("Já existe um lote com este nome para o fornecedor informado.");
        }

        entity.setName(request.name());
        entity.setExpirationDate(request.expirationDate());
        entity.setSupplier(supplier);

        Batch saved = repository.save(entity);

        return modelMapper.map(saved, BatchResponseDto.class);
    }

    @Transactional
    public void delete(UUID id) {
        log.info("Deletando lote Id: {}", id);
        if (!repository.existsById(id)) {
            throw new NotFoundException("Batch %s not found".formatted(id));
        }
        repository.deleteById(id);
        log.info("Lote deletado: {}", id);
    }
}
