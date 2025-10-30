package com.aurawave.service;

import com.aurawave.dao.ItemDao;
import com.aurawave.domain.model.Item;
import com.aurawave.dto.itemDto.ItemRequestDto;
import com.aurawave.dto.itemDto.ItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemDao itemDao;
    private final ModelMapper mapper;

    public ItemResponseDto create(ItemRequestDto dto) {
        Item item = mapper.map(dto, Item.class);
        Long id = itemDao.create(item);
        Item saved = itemDao.getById(id);
        return mapper.map(saved, ItemResponseDto.class);
    }

    public ItemResponseDto update(Long id, ItemRequestDto dto) {
        Item item = mapper.map(dto, Item.class);
        itemDao.update(id, item);
        Item updated = itemDao.getById(id);
        return mapper.map(updated, ItemResponseDto.class);
    }

    public ItemResponseDto getById(Long id) {
        return mapper.map(itemDao.getById(id), ItemResponseDto.class);
    }

    public List<ItemResponseDto> getAll() {
        return itemDao.getAll().stream()
                .map(i -> mapper.map(i, ItemResponseDto.class))
                .toList();
    }

    public void delete(Long id) {
        itemDao.delete(id);
    }
}
