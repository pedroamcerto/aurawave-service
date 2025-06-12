package com.aurawave.service;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.enumerated.ItemStatus;
import com.aurawave.domain.interfaces.ServiceInterface;
import com.aurawave.domain.model.Item;
import com.aurawave.domain.model.Warehouse;
import com.aurawave.dto.laboratory.GetLaboratoryDto;
import com.aurawave.dto.warehouse.CreateWarehouseDto;
import com.aurawave.dto.warehouse.GetWarehouseDto;
import com.aurawave.repository.ItemRepository;
import com.aurawave.repository.WarehouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService implements ServiceInterface<GetWarehouseDto, CreateWarehouseDto> {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private LaboratoryService laboratoryService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Cria um novo almoxarifado.
     *
     * @param createWarehouseDto O DTO com os dados do almoxarifado a ser criado.
     */
    @Override
    public void create(CreateWarehouseDto createWarehouseDto) {
        if (!returnLaboratoryId(createWarehouseDto)) {
            throw new NotFoundException("Laboratório não encontrado");
        }

        Warehouse warehouse = modelMapper.map(createWarehouseDto, Warehouse.class);
        warehouseRepository.save(warehouse);
    }

    /**
     * Verifica se o laboratório está presente e retorna o ID.
     *
     * @param createWarehouseDto O DTO com os dados do almoxarifado.
     * @return boolean indicando se o laboratório existe.
     */
    private boolean returnLaboratoryId(CreateWarehouseDto createWarehouseDto) {
        return Optional.ofNullable(createWarehouseDto)
                .map(CreateWarehouseDto::getLaboratory)
                .map(GetLaboratoryDto::getId)
                .map(laboratoryService::getById)
                .isPresent();
    }

    /**
     * Atualiza os dados de um almoxarifado.
     *
     * @param id O ID do almoxarifado a ser atualizado.
     * @param createWarehouseDto O DTO com os dados atualizados do almoxarifado.
     */
    @Override
    public void update(Long id, CreateWarehouseDto createWarehouseDto) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Almoxarifado não encontrado"));
        modelMapper.map(createWarehouseDto, warehouse);
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
                .orElseThrow(() -> new NotFoundException("Almoxarifado não encontrado"));
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

    /**
     * Adiciona um item ao almoxarifado.
     *
     * @param warehouseId O ID do almoxarifado.
     * @param itemId O ID do item a ser adicionado.
     */
    public void addItemToWarehouse(Long warehouseId, Long itemId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NotFoundException("Almoxarifado não encontrado"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item não encontrado"));

        item.setStatus(ItemStatus.AVALIABLE);

        warehouse.getItems().add(item);
        warehouseRepository.save(warehouse);
    }

    /**
     * Remove um item do almoxarifado.
     *
     * @param warehouseId O ID do almoxarifado.
     * @param itemId O ID do item a ser removido.
     */
    public void removeItemFromWarehouse(Long warehouseId, Long itemId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NotFoundException("Almoxarifado não encontrado"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item não encontrado"));

        item.setStatus(ItemStatus.UNAVAILABLE);

        itemRepository.save(item);
        warehouseRepository.save(warehouse);
    }

    /**
     * Deleta um almoxarifado pelo ID.
     *
     * @param id O ID do almoxarifado a ser deletado.
     */
    @Override
    public void delete(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Almoxarifado não encontrado"));
        warehouseRepository.delete(warehouse);
    }
}