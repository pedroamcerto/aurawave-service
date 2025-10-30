package com.aurawave.service;

import com.aurawave.dao.ModelDao;
import com.aurawave.domain.model.Model;
import com.aurawave.dto.modelDto.ModelRequestDto;
import com.aurawave.dto.modelDto.ModelResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelDao modelDao;
    private final ModelMapper mapper;

    public ModelResponseDto create(ModelRequestDto dto) {
        Model model = mapper.map(dto, Model.class);
        Long id = modelDao.create(model);
        Model saved = modelDao.getById(id);
        return mapper.map(saved, ModelResponseDto.class);
    }

    public ModelResponseDto update(Long id, ModelRequestDto dto) {
        Model model = mapper.map(dto, Model.class);
        modelDao.update(id, model);
        Model updated = modelDao.getById(id);
        return mapper.map(updated, ModelResponseDto.class);
    }

    public ModelResponseDto getById(Long id) {
        return mapper.map(modelDao.getById(id), ModelResponseDto.class);
    }

    public List<ModelResponseDto> getAll() {
        return modelDao.getAll().stream()
                .map(m -> mapper.map(m, ModelResponseDto.class))
                .toList();
    }

    public void delete(Long id) {
        modelDao.delete(id);
    }
}
