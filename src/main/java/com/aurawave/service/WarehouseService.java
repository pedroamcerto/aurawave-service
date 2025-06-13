package com.aurawave.service;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.ServiceInterface;
import com.aurawave.domain.model.Warehouse;
import com.aurawave.dto.warehouse.CreateWarehouseDto;
import com.aurawave.dto.warehouse.GetWarehouseDto;
import com.aurawave.repository.ItemRepository;
import com.aurawave.repository.LaboratoryRepository;
import com.aurawave.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService implements ServiceInterface<GetWarehouseDto, CreateWarehouseDto> {

    private final WarehouseRepository warehouseRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    private static final String NOT_FOUND_MESSAGE = "Almoxarifado não encontrado";

    /**
     * Cria um novo almoxarifado.
     *
     * @param createWarehouseDto O DTO com os dados do almoxarifado a ser criado.
     */
    @Override
    public void create(CreateWarehouseDto createWarehouseDto) {
        laboratoryRepository.findById(createWarehouseDto.getLaboratory().getId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));

        Warehouse warehouse = modelMapper.map(createWarehouseDto, Warehouse.class);

        warehouseRepository.save(warehouse);
    }

    /**
     * Recupera um almoxarifado pelo ID.
     *
     * @param id O ID do almoxarifado.
     * @return O DTO com os dados do almoxarifado.
     */
    @Override
    public GetWarehouseDto getById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        return modelMapper.map(warehouse, GetWarehouseDto.class);
    }

    /**
     * Recupera todos os almoxarifados cadastrados.
     *
     * @return Uma lista de DTOs com todos os almoxarifados.
     */
    @Override
    public List<GetWarehouseDto> getAll() {
        return warehouseRepository.findAll().stream()
                .map(warehouse -> modelMapper.map(warehouse, GetWarehouseDto.class))
                .toList();
    }
}