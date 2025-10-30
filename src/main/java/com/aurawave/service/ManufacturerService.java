package com.aurawave.service;

import com.aurawave.dao.ManufacturerDao;
import com.aurawave.domain.model.Manufacturer;
import com.aurawave.dto.manufacturerDto.ManufacturerRequestDto;
import com.aurawave.dto.manufacturerDto.ManufacturerResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerDao manufacturerDao;
    private final ModelMapper mapper;

    public ManufacturerResponseDto create(ManufacturerRequestDto dto) {
        Manufacturer manufacturer = mapper.map(dto, Manufacturer.class);
        Long id = manufacturerDao.create(manufacturer);
        Manufacturer saved = manufacturerDao.getById(id);
        return mapper.map(saved, ManufacturerResponseDto.class);
    }

    public ManufacturerResponseDto update(Long id, ManufacturerRequestDto dto) {
        Manufacturer manufacturer = mapper.map(dto, Manufacturer.class);
        manufacturerDao.update(id, manufacturer);
        Manufacturer updated = manufacturerDao.getById(id);
        return mapper.map(updated, ManufacturerResponseDto.class);
    }

    public ManufacturerResponseDto getById(Long id) {
        return mapper.map(manufacturerDao.getById(id), ManufacturerResponseDto.class);
    }

    public List<ManufacturerResponseDto> getAll() {
        return manufacturerDao.getAll().stream()
                .map(m -> mapper.map(m, ManufacturerResponseDto.class))
                .toList();
    }

    public void delete(Long id) {
        manufacturerDao.delete(id);
    }
}
