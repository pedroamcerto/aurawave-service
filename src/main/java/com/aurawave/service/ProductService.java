package com.aurawave.service;

import com.aurawave.dao.ProductDao;
import com.aurawave.dao.WarehouseDao;
import com.aurawave.domain.enumerated.ProductStatus;
import com.aurawave.domain.model.Product;
import com.aurawave.dto.productDto.ProductRequestDto;
import com.aurawave.dto.productDto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final WarehouseDao warehouseDao;
    private final ModelMapper mapper;

    public ProductResponseDto create(ProductRequestDto dto) {
        if (!warehouseDao.existsById(dto.getWarehouseId())) {
            throw new com.aurawave.core.exception.NotFoundException(
                    "Warehouse (warehouseId=" + dto.getWarehouseId() + ") não encontrado");
        }

        Product p = mapper.map(dto, Product.class);
        Long id = productDao.create(p);
        Product saved = productDao.getById(id);
        return mapper.map(saved, ProductResponseDto.class);
    }

    public ProductResponseDto update(Long id, ProductRequestDto dto) {
        if (!warehouseDao.existsById(dto.getWarehouseId())) {
            throw new com.aurawave.core.exception.NotFoundException(
                    "Warehouse (warehouseId=" + dto.getWarehouseId() + ") não encontrado");
        }

        Product p = mapper.map(dto, Product.class);
        productDao.update(id, p);
        Product updated = productDao.getById(id);
        return mapper.map(updated, ProductResponseDto.class);
    }

    public ProductResponseDto getById(Long id) {
        return mapper.map(productDao.getById(id), ProductResponseDto.class);
    }

    public List<ProductResponseDto> getAll() {
        return productDao.getAll().stream()
                .map(prod -> mapper.map(prod, ProductResponseDto.class))
                .toList();
    }

    public void delete(Long id) {
        productDao.delete(id);
    }

    public void validValidityProduct() {
        log.atInfo().log("Verificando a data de vencimento dos produtos.");

        long start = System.currentTimeMillis();

        List<ProductResponseDto> responseDtos = getAll();

        if (responseDtos.isEmpty()) {
            log.atInfo().log("Nenhum produto encontrado. Fim do processamento.");
            return;
        }

        log.atInfo().log("Foram recuperados {} produto{}da base.", responseDtos.size(), (responseDtos.size() == 1) ? "" : "s");

        LocalDateTime currentDate = LocalDateTime.now();

        responseDtos.stream()
                .filter(Objects::nonNull)
                .filter(response -> response.getValidityDate() != null)
                .filter(response -> response.getValidityDate().isBefore(currentDate))
                .forEach(response -> {
                    response.setStatus(ProductStatus.EXPIRED);

                    update(response.getId(),
                            new ProductRequestDto(
                                    response.getName(),
                                    response.getValidityDate(),
                                    response.getWarehouseId(),
                                    response.getCostPrice(),
                                    response.getStatus()
                            ));
                });

        log.atInfo().log("Fim do processamento. {} ms", System.currentTimeMillis() - start);
    }
}
