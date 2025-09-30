package com.aurawave.service;

import com.aurawave.core.domain.Manufacturer;
import com.aurawave.core.exception.ConflictException;
import com.aurawave.core.exception.NotFoundException;
import com.aurawave.dto.PageResponseDto;
import com.aurawave.dto.manufacturerDto.ManufacturerRequestDto;
import com.aurawave.dto.manufacturerDto.ManufacturerResponseDto;
import com.aurawave.dto.supplierDto.SupplierResponseDto;
import com.aurawave.repository.ManufacturerRepository;
import com.aurawave.util.ModelMapperUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository repository;
    private final ModelMapper modelMapper;

    @Transactional
    public ManufacturerResponseDto create(ManufacturerRequestDto request) {
        verifyExistManufacturer(request.name());

        Manufacturer entity = modelMapper.map(request, Manufacturer.class);
        repository.save(entity);

        log.info("Novo fabricante adicionado com sucesso: {} - {}.", entity.getId(), entity.getName());
        return ModelMapperUtil.convertEntityToDto(entity, ManufacturerResponseDto.class);
    }

    public PageResponseDto<ManufacturerResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ManufacturerResponseDto> pageDto = repository.findAll(pageable)
                .map(m -> modelMapper.map(m, ManufacturerResponseDto.class));

        if (pageDto.isEmpty()) {
            throw new NotFoundException("Nenhum fabricante foi encontrado.");
        }
        return PageResponseDto.of(pageDto);
    }

    public ManufacturerResponseDto findById(UUID id) {
        Manufacturer entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Manufacturer %s not found".formatted(id)));
        return modelMapper.map(entity, ManufacturerResponseDto.class);
    }

    public Long count() {
        log.info("Buscando a quantidade de fabricantes.");
        Long count = repository.count();
        log.info("Quantidade de fabricantes: {}", count);
        return count;
    }

    @Transactional
    public ManufacturerResponseDto update(UUID id, ManufacturerRequestDto request) {
        log.info("Atualizando fabricante Id: {}", id);
        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Manufacturer %s not found".formatted(id)));
        entity.setName(request.name());
        return modelMapper.map(repository.save(entity), ManufacturerResponseDto.class);
    }

    @Transactional
    public void delete(UUID id) {
        log.info("Deletando fabricante Id: {}", id);
        if (!repository.existsById(id)) {
            throw new NotFoundException("Manufacturer %s not found".formatted(id));
        }
        repository.deleteById(id);
        log.info("Fabricante deletado: {}", id);
    }

    public Manufacturer getEntityByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Manufacturer %s not found".formatted(id)));
    }

    public void verifyExistManufacturer(String name) {
        log.info("Verificando a existência do fabricante: {}", name);
        if (repository.existsByName(name)) throw new ConflictException("Esse fabricante já está cadastrado.");
    }
}
