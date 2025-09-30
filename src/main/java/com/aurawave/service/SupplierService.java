package com.aurawave.service;

import com.aurawave.core.domain.Supplier;
import com.aurawave.core.exception.ConflictException;
import com.aurawave.core.exception.NotFoundException;
import com.aurawave.dto.PageResponseDto;
import com.aurawave.dto.supplierDto.SupplierRequestDto;
import com.aurawave.dto.supplierDto.SupplierResponseDto;
import com.aurawave.repository.SupplierRepository;
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
public class SupplierService {

    private final SupplierRepository repository;
    private final ModelMapper modelMapper;

    @Transactional
    public SupplierResponseDto create(SupplierRequestDto request) {
        verifyExistSupplier(request.name());

        Supplier supplier = modelMapper.map(request, Supplier.class);
        repository.save(supplier);
        log.info("Nova fornecedor adicionado com sucesso: {} - {}.", supplier.getId(), supplier.getName());

        return ModelMapperUtil.convertEntityToDto(supplier, SupplierResponseDto.class);
    }

    public PageResponseDto<SupplierResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SupplierResponseDto> supplierResponseDtos = repository.findAll(pageable)
                .map(supplier -> modelMapper.map(supplier, SupplierResponseDto.class));

        if (supplierResponseDtos.isEmpty()) {
            throw new NotFoundException("Nem um fornecedor foi encontrado.");
        }
        return PageResponseDto.of(supplierResponseDtos);
    }

    public SupplierResponseDto findById(UUID id) {
        var supplier = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier %s not found".formatted(id)));
        return modelMapper.map(supplier, SupplierResponseDto.class);
    }

    public Long count() {
        log.info("Buscando a quantidade de fornecedores.");
        Long count = repository.count();
        log.info("Quantidade de fornecedores: {}", count);
        return count;
    }

    @Transactional
    public SupplierResponseDto update(UUID id, SupplierRequestDto dto) {
        log.info("Atualizando o fornecedor com Id: {}", id);
        var entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Supplier %s not found".formatted(id)));
        entity.setName(dto.name());
        return modelMapper.map(repository.save(entity), SupplierResponseDto.class);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) throw new NotFoundException("Supplier %s not found".formatted(id));
        log.info("Deletando o fornecedor com Id: {}", id);
        repository.deleteById(id);
    }

    public void verifyExistSupplier(String name) {
        log.info("Verificando a existência do fornecedor: {}", name);
        if (repository.existsByName(name)) throw new ConflictException("Essa fornecedor já está cadastrado.");
    }
}