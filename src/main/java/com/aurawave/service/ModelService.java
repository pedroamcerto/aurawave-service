package com.aurawave.service;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.ServiceInterface;
import com.aurawave.domain.model.Model;
import com.aurawave.dto.model.CreateModelDto;
import com.aurawave.dto.model.GetModelDto;
import com.aurawave.repository.ModelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsável pelas operações CRUD para a entidade Model.
 * Implementa a interface {@link ServiceInterface} para fornecer as operações de criação,
 * atualização, remoção e busca de modelos.
 */
@Service
public class ModelService implements ServiceInterface<GetModelDto, CreateModelDto> {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Cria um novo modelo.
     *
     * Este método mapeia o DTO de entrada {@link CreateModelDto} para a entidade Model
     * e persiste o novo modelo no banco de dados.
     *
     * @param entity O DTO contendo os dados do modelo a ser criado.
     */
    @Override
    public void create(CreateModelDto entity) {
        Model model = modelMapper.map(entity, Model.class);
        modelRepository.save(model);
    }

    /**
     * Atualiza um modelo existente.
     *
     * Este método busca o modelo por ID, atualiza seus dados e salva as mudanças no banco de dados.
     *
     * @param id O ID do modelo a ser atualizado.
     * @param entity O DTO com os dados atualizados do modelo.
     */
    @Override
    public void update(Long id, CreateModelDto entity) {
        Model model = modelRepository.findById(id).orElseThrow(() -> new NotFoundException("Modelo não encontrado"));
        modelMapper.map(entity, model);
        modelRepository.save(model);
    }

    /**
     * Recupera um modelo pelo seu ID.
     *
     * Este método busca um modelo no banco de dados e retorna um DTO com seus dados.
     *
     * @param id O ID do modelo a ser recuperado.
     * @return O DTO {@link GetModelDto} com os dados do modelo encontrado.
     */
    @Override
    public GetModelDto getById(Long id) {
        Model model = modelRepository.findById(id).orElseThrow(() -> new NotFoundException("Modelo não encontrado"));
        return modelMapper.map(model, GetModelDto.class);
    }

    /**
     * Recupera todos os modelos cadastrados no banco de dados.
     *
     * Este método retorna todos os modelos armazenados no banco de dados como uma lista de DTOs.
     *
     * @return Uma lista de DTOs {@link GetModelDto} com os dados de todos os modelos.
     */
    @Override
    public List<GetModelDto> getAll() {
        return modelRepository.findAll().stream()
                .map(model -> modelMapper.map(model, GetModelDto.class))
                .collect(Collectors.toList());
    }

}