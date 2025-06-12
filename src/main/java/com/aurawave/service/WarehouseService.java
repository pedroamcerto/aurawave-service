package com.aurawave.service;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.ServiceInterface;
import com.aurawave.domain.model.Laboratory;
import com.aurawave.domain.model.Warehouse;
import com.aurawave.dto.warehouse.CreateWarehouseDto;
import com.aurawave.dto.warehouse.GetWarehouseDto;
import com.aurawave.repository.LaboratoryRepository;
import com.aurawave.repository.WarehouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WarehouseService implements ServiceInterface<GetWarehouseDto, CreateWarehouseDto> {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private LaboratoryService laboratoryService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void create(CreateWarehouseDto createWarehouseDto) {
        if (!returnLaboratoryId(createWarehouseDto)) {
            throw new NotFoundException("Laboratório não encontrado");
        }

        Warehouse warehouse = modelMapper.map(createWarehouseDto, Warehouse.class);
        warehouseRepository.save(warehouse);
    }

    private boolean returnLaboratoryId(CreateWarehouseDto createWarehouseDto) {
        return Optional.ofNullable(createWarehouseDto)
                .map(CreateWarehouseDto::getLaboratory)
                .map(Laboratory::getId)
                .map(laboratoryService::getById)
                .isPresent();
    }

    @Override
    public void update(Long id, CreateWarehouseDto createWarehouseDto) {
        ServiceInterface.super.update(id, createWarehouseDto);
    }

    @Override
    public GetWarehouseDto getById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Almoraxarifado não encontrado"));
        return modelMapper.map(warehouse, GetWarehouseDto.class);
    }

    @Override
    public List<GetWarehouseDto> getAll() {
        return warehouseRepository.findAll().stream()
                .map(warehouse -> modelMapper.map(warehouse, GetWarehouseDto.class))
                .toList();
    }
}
