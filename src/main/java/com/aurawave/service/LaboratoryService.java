package com.aurawave.service;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.ServiceInterface;
import com.aurawave.domain.model.Laboratory;
import com.aurawave.dto.laboratory.CreateLaboratoryDto;
import com.aurawave.dto.laboratory.GetLaboratoryDto;
import com.aurawave.repository.LaboratoryRepository;
import com.aurawave.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável por manipular as operações relacionadas aos Laboratórios.
 * Implementa a interface {@link ServiceInterface} para fornecer as operações de CRUD
 * para a entidade Laboratory.
 *
 * A classe {@link LaboratoryService} interage com o repositório {@link LaboratoryRepository}
 * para acessar e persistir dados dos laboratórios. Utiliza o {@link ModelMapper} para realizar
 * a conversão entre as entidades e os DTOs.
 *
 * @see LaboratoryRepository
 * @see ModelMapper
 */
@Service
@RequiredArgsConstructor
public class LaboratoryService implements ServiceInterface<GetLaboratoryDto, CreateLaboratoryDto> {

    private final LaboratoryRepository laboratoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final ModelMapper modelMapper;

    private static final String NOT_FOUND_MESSAGE = "Laboratorio não encontrado";

    /**
     * Cria um novo laboratório.
     *
     * @param createLaboratoryDto DTO contendo os dados do laboratório a ser criado.
     */
    @Override
    public void create(CreateLaboratoryDto createLaboratoryDto) {
        Laboratory laboratory = modelMapper.map(createLaboratoryDto, Laboratory.class);
        laboratoryRepository.save(laboratory);
    }

    /**
     * Recupera um laboratório pelo seu ID.
     *
     * @param id O ID do laboratório a ser recuperado.
     * @return O DTO com os dados do laboratório.
     * @throws NotFoundException Se o laboratório não for encontrado no banco de dados.
     */
    @Override
    public GetLaboratoryDto getById(Long id) {
        Laboratory laboratory = laboratoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        return modelMapper.map(laboratory, GetLaboratoryDto.class);
    }

    /**
     * Recupera todos os laboratórios cadastrados no banco de dados.
     *
     * @return Uma lista de DTOs com os dados de todos os laboratórios.
     */
    @Override
    public List<GetLaboratoryDto> getAll() {
        return laboratoryRepository.findAll().stream()
                .map(laboratory -> modelMapper.map(laboratory, GetLaboratoryDto.class))
                .toList();
    }
}