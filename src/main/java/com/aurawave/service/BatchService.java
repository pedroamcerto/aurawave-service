package com.aurawave.service;

import com.aurawave.core.domain.Batch;
import com.aurawave.repository.BatchRepository;
import com.aurawave.repository.SupplierRepository;
import com.aurawave.core.exception.NotFoundException;
import com.aurawave.dto.batchDto.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepository batchRepo;
    private final SupplierRepository supplierRepo;
    private final ModelMapper modelMapper;

    @Transactional
    public BatchResponseDto create(BatchRequestDto dto) {
        var supplier = supplierRepo.findById(dto.getSupplierId())
                .orElseThrow(() -> new NotFoundException("Supplier %s not found".formatted(dto.getSupplierId())));
        var entity = modelMapper.map(dto, Batch.class);
        entity.setSupplier(supplier);
        var saved = batchRepo.save(entity);
        return modelMapper.map(saved, BatchResponseDto.class);
    }

    @Transactional(readOnly = true)
    public Page<BatchResponseDto> findAll(Pageable pageable) {
        return batchRepo.findAll(pageable).map(b -> modelMapper.map(b, BatchResponseDto.class));
    }

    @Transactional(readOnly = true)
    public BatchResponseDto findById(UUID id) {
        var entity = batchRepo.findById(id).orElseThrow(() -> new NotFoundException("Batch %s not found".formatted(id)));
        return modelMapper.map(entity, BatchResponseDto.class);
    }

    @Transactional
    public BatchResponseDto update(UUID id, BatchRequestDto dto) {
        var entity = batchRepo.findById(id).orElseThrow(() -> new NotFoundException("Batch %s not found".formatted(id)));
        var supplier = supplierRepo.findById(dto.getSupplierId())
                .orElseThrow(() -> new NotFoundException("Supplier %s not found".formatted(dto.getSupplierId())));
        entity.setName(dto.getName());
        entity.setExpirationDate(dto.getExpirationDate());
        entity.setSupplier(supplier);
        return modelMapper.map(batchRepo.save(entity), BatchResponseDto.class);
    }

    @Transactional
    public void delete(UUID id) {
        if (!batchRepo.existsById(id)) throw new NotFoundException("Batch %s not found".formatted(id));
        batchRepo.deleteById(id);
    }
}
