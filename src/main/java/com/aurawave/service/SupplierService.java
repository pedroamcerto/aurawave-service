package com.aurawave.service;

import com.aurawave.dao.SupplierDao;
import com.aurawave.domain.model.Supplier;
import com.aurawave.dto.supplierDto.SupplierRequestDto;
import com.aurawave.dto.supplierDto.SupplierResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierDao supplierDao;
    private final ModelMapper mapper;

    public SupplierResponseDto create(SupplierRequestDto dto) {
        Supplier supplier = mapper.map(dto, Supplier.class);
        Long id = supplierDao.create(supplier);
        Supplier saved = supplierDao.getById(id);
        return mapper.map(saved, SupplierResponseDto.class);
    }

    public SupplierResponseDto update(Long id, SupplierRequestDto dto) {
        Supplier supplier = mapper.map(dto, Supplier.class);
        supplierDao.update(id, supplier);
        Supplier updated = supplierDao.getById(id);
        return mapper.map(updated, SupplierResponseDto.class);
    }

    public SupplierResponseDto getById(Long id) {
        return mapper.map(supplierDao.getById(id), SupplierResponseDto.class);
    }

    public List<SupplierResponseDto> getAll() {
        return supplierDao.getAll().stream()
                .map(s -> mapper.map(s, SupplierResponseDto.class))
                .toList();
    }

    public void delete(Long id) {
        supplierDao.delete(id);
    }
}
