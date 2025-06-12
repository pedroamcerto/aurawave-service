package com.aurawave.service;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.ServiceInterface;
import com.aurawave.domain.model.Laboratory;
import com.aurawave.dto.laboratory.CreateLaboratoryDto;
import com.aurawave.dto.laboratory.GetLaboratoryDto;
import com.aurawave.repository.LaboratoryRepository;
import com.aurawave.repository.WarehouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LaboratoryService implements ServiceInterface<GetLaboratoryDto, CreateLaboratoryDto> {

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ModelMapper modelMapper;

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
        Laboratory laboratory = laboratoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Laboratório não encontrado"));
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

    /**
     * Deleta um laboratório pelo seu ID. Se um laboratório for deletado, os almoxarifados associados a ele serão deletados também.
     *
     * @param id O ID do laboratório a ser deletado.
     */
    @Override
    public void delete(Long id) {
        Laboratory laboratory = laboratoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Laboratório não encontrado"));

        warehouseRepository.deleteAll(laboratory.getWarehouses());
        laboratoryRepository.delete(laboratory);
    }
}