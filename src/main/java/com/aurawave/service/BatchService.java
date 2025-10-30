package com.aurawave.service;

import com.aurawave.dao.BatchDao;
import com.aurawave.domain.model.Batch;
import com.aurawave.dto.batchDto.BatchRequestDto;
import com.aurawave.dto.batchDto.BatchResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchDao batchDao;
    private final ModelMapper mapper;

    public BatchResponseDto create(BatchRequestDto dto) {
        Batch batch = mapper.map(dto, Batch.class);
        Long id = batchDao.create(batch);
        Batch saved = batchDao.getById(id);
        return mapper.map(saved, BatchResponseDto.class);
    }

    public BatchResponseDto update(Long id, BatchRequestDto dto) {
        Batch batch = mapper.map(dto, Batch.class);
        batchDao.update(id, batch);
        Batch updated = batchDao.getById(id);
        return mapper.map(updated, BatchResponseDto.class);
    }

    public BatchResponseDto getById(Long id) {
        return mapper.map(batchDao.getById(id), BatchResponseDto.class);
    }

    public List<BatchResponseDto> getAll() {
        return batchDao.getAll().stream()
                .map(b -> mapper.map(b, BatchResponseDto.class))
                .toList();
    }

    public void delete(Long id) {
        batchDao.delete(id);
    }
}
