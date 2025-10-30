package com.aurawave.service;

import com.aurawave.dao.CollaboratorDao;
import com.aurawave.domain.model.Collaborator;
import com.aurawave.dto.collaboratorDto.CollaboratorRequestDto;
import com.aurawave.dto.collaboratorDto.CollaboratorResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollaboratorService {

    private final CollaboratorDao collaboratorDao;
    private final ModelMapper mapper;

    public CollaboratorResponseDto create(CollaboratorRequestDto dto) {
        Collaborator collaborator = mapper.map(dto, Collaborator.class);
        Long id = collaboratorDao.create(collaborator);
        Collaborator saved = collaboratorDao.getById(id);
        return mapper.map(saved, CollaboratorResponseDto.class);
    }

    public CollaboratorResponseDto update(Long id, CollaboratorRequestDto dto) {
        Collaborator collaborator = mapper.map(dto, Collaborator.class);
        collaboratorDao.update(id, collaborator);
        Collaborator updated = collaboratorDao.getById(id);
        return mapper.map(updated, CollaboratorResponseDto.class);
    }

    public CollaboratorResponseDto getById(Long id) {
        return mapper.map(collaboratorDao.getById(id), CollaboratorResponseDto.class);
    }

    public List<CollaboratorResponseDto> getAll() {
        return collaboratorDao.getAll().stream()
                .map(c -> mapper.map(c, CollaboratorResponseDto.class))
                .toList();
    }

    public void delete(Long id) {
        collaboratorDao.delete(id);
    }
}
