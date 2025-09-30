package com.aurawave.service;

import com.aurawave.core.domain.Product;
import com.aurawave.core.exception.ConflictException;
import com.aurawave.core.exception.NotFoundException;
import com.aurawave.dto.PageResponseDto;
import com.aurawave.dto.productDto.ProductRequestDto;
import com.aurawave.dto.productDto.ProductResponseDto;
import com.aurawave.repository.ProductRepository;
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
public class ProductService {

    private final ProductRepository repository;
    private final ModelMapper modelMapper;

    @Transactional
    public ProductResponseDto create(ProductRequestDto request) {
        verifyExistProduct(request.name());

        Product entity = modelMapper.map(request, Product.class);
        repository.save(entity);

        log.info("Novo produto adicionado com sucesso: {} - {}.", entity.getId(), entity.getName());
        return ModelMapperUtil.convertEntityToDto(entity, ProductResponseDto.class);
    }

    public PageResponseDto<ProductResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponseDto> pageDto = repository.findAll(pageable)
                .map(m -> modelMapper.map(m, ProductResponseDto.class));

        if (pageDto.isEmpty()) {
            throw new NotFoundException("Nenhum produto foi encontrado.");
        }
        return PageResponseDto.of(pageDto);
    }

    public ProductResponseDto findById(UUID id) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product %s not found".formatted(id)));
        return modelMapper.map(entity, ProductResponseDto.class);
    }

    public Long count() {
        log.info("Buscando a quantidade de produtos.");
        Long count = repository.count();
        log.info("Quantidade de produtos: {}", count);
        return count;
    }

    @Transactional
    public ProductResponseDto update(UUID id, ProductRequestDto request) {
        log.info("Atualizando produto Id: {}", id);

        Product entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product %s not found".formatted(id)));

        if (repository.existsByNameAndIdNot(request.name(), id)) {
            throw new ConflictException("Já existe um produto com esse nome.");
        }

        entity.setName(request.name());
        Product saved = repository.save(entity);

        return modelMapper.map(saved, ProductResponseDto.class);
    }

    @Transactional
    public void delete(UUID id) {
        log.info("Deletando produto Id: {}", id);
        if (!repository.existsById(id)) {
            throw new NotFoundException("Product %s not found".formatted(id));
        }
        repository.deleteById(id);
        log.info("Produto deletado: {}", id);
    }

    public Product getEntityByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product %s not found".formatted(id)));
    }

    public void verifyExistProduct(String name) {
        log.info("Verificando a existência do produto: {}", name);
        if (repository.existsByName(name)) {
            throw new ConflictException("Esse produto já está cadastrado.");
        }
    }
}
