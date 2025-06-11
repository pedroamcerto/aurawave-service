package com.aurawave.service;

import com.aurawave.domain.interfaces.ServiceInterface;
import com.aurawave.dto.laboratory.CreateLaboratoryDto;
import com.aurawave.dto.laboratory.GetLaboratoryDto;
import com.aurawave.repository.LaboratoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaboratoryService implements ServiceInterface<GetLaboratoryDto, CreateLaboratoryDto> {

    @Autowired
    private LaboratoryRepository laboratoryRepository;


    @Override
    public void create(CreateLaboratoryDto createLaboratoryDto) {

    }

    @Override
    public void update(Long id, CreateLaboratoryDto createLaboratoryDto) {

    }

    @Override
    public GetLaboratoryDto getById(Long id) {
        return null;
    }

    @Override
    public List<GetLaboratoryDto> getAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
