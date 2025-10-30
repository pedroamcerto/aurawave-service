package com.aurawave.service;

import com.aurawave.dao.LaboratoryDao;
import com.aurawave.domain.model.Laboratory;
import com.aurawave.dto.laboratoryDto.LaboratoryRequestDto;
import com.aurawave.dto.laboratoryDto.LaboratoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratoryService {

    private final LaboratoryDao laboratoryDao;
    private final ModelMapper mapper;

    public LaboratoryResponseDto create(LaboratoryRequestDto dto) {
        Laboratory laboratory = mapper.map(dto, Laboratory.class);
        Long id = laboratoryDao.create(laboratory);
        Laboratory saved = laboratoryDao.getById(id);
        return mapper.map(saved, LaboratoryResponseDto.class);
    }

    public LaboratoryResponseDto update(Long id, LaboratoryRequestDto dto) {
        Laboratory laboratory = mapper.map(dto, Laboratory.class);
        laboratoryDao.update(id, laboratory);
        Laboratory updated = laboratoryDao.getById(id);
        return mapper.map(updated, LaboratoryResponseDto.class);
    }

    public LaboratoryResponseDto getById(Long id) {
        return mapper.map(laboratoryDao.getById(id), LaboratoryResponseDto.class);
    }

    public List<LaboratoryResponseDto> getAll() {
        return laboratoryDao.getAll().stream()
                .map(l -> mapper.map(l, LaboratoryResponseDto.class))
                .toList();
    }

    public void delete(Long id) {
        laboratoryDao.delete(id);
    }
}
