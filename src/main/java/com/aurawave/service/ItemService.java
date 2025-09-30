package com.aurawave.service;

import com.aurawave.core.domain.Item;
import com.aurawave.core.exception.NotFoundException;
import com.aurawave.dto.itemDto.ItemRequestDto;
import com.aurawave.dto.itemDto.ItemResponseDto;
import com.aurawave.repository.BatchRepository;
import com.aurawave.repository.ItemRepository;
import com.aurawave.repository.ModelRepository;
import com.aurawave.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepo;
    private final BatchRepository batchRepo;
    private final WarehouseRepository warehouseRepo;
    private final ModelRepository modelRepo;
    private final ModelMapper modelMapper;

    @Transactional
    public ItemResponseDto create(ItemRequestDto dto) {
        var item = modelMapper.map(dto, Item.class);

        var batch = batchRepo.findById(dto.getBatchId())
                .orElseThrow(() -> new NotFoundException("Batch %s not found".formatted(dto.getBatchId())));
        var wh = warehouseRepo.findById(dto.getWarehouseId())
                .orElseThrow(() -> new NotFoundException("Warehouse %s not found".formatted(dto.getWarehouseId())));
        var model = modelRepo.findById(dto.getModelId())
                .orElseThrow(() -> new NotFoundException("Model %s not found".formatted(dto.getModelId())));

        item.setBatch(batch);
        item.setWarehouse(wh);
        item.setModel(model);

        var saved = itemRepo.save(item);
        return modelMapper.map(saved, ItemResponseDto.class);
    }

    @Transactional(readOnly = true)
    public Page<ItemResponseDto> findAll(Pageable pageable) {
        return itemRepo.findAll(pageable).map(e -> modelMapper.map(e, ItemResponseDto.class));
    }

    @Transactional(readOnly = true)
    public ItemResponseDto findById(UUID id) {
        var entity = itemRepo.findById(id).orElseThrow(() -> new NotFoundException("Item %s not found".formatted(id)));
        return modelMapper.map(entity, ItemResponseDto.class);
    }

    @Transactional
    public ItemResponseDto update(UUID id, ItemRequestDto dto) {
        var entity = itemRepo.findById(id).orElseThrow(() -> new NotFoundException("Item %s not found".formatted(id)));

        var batch = batchRepo.findById(dto.getBatchId())
                .orElseThrow(() -> new NotFoundException("Batch %s not found".formatted(dto.getBatchId())));
        var wh = warehouseRepo.findById(dto.getWarehouseId())
                .orElseThrow(() -> new NotFoundException("Warehouse %s not found".formatted(dto.getWarehouseId())));
        var model = modelRepo.findById(dto.getModelId())
                .orElseThrow(() -> new NotFoundException("Model %s not found".formatted(dto.getModelId())));

        entity.setName(dto.getName());
        entity.setExpirationDate(dto.getExpirationDate());
        entity.setStatus(dto.getStatus());
        entity.setBatch(batch);
        entity.setWarehouse(wh);
        entity.setModel(model);

        return modelMapper.map(itemRepo.save(entity), ItemResponseDto.class);
    }

    @Transactional
    public void delete(UUID id) {
        if (!itemRepo.existsById(id)) throw new NotFoundException("Item %s not found".formatted(id));
        itemRepo.deleteById(id);
    }
}
