package com.aurawave.service;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.ServiceInterface;
import com.aurawave.domain.model.Model;
import com.aurawave.dto.model.CreateModelDto;
import com.aurawave.dto.model.GetModelDto;
import com.aurawave.repository.ModelRepository;
import com.aurawave.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsável pelas operações CRUD para a entidade Model.
 * Implementa a interface {@link ServiceInterface} para fornecer as operações de criação,
 * atualização, remoção e busca de modelos.
 */
@Service
@RequiredArgsConstructor
public class ModelService implements ServiceInterface<GetModelDto, CreateModelDto> {

    private final ModelRepository modelRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    private static final String NOT_FOUND_MESSAGE = "Model não encotrado";

    /**
     * Cria um novo modelo.
     *
     * Este método mapeia o DTO de entrada {@link CreateModelDto} para a entidade Model
     * e persiste o novo modelo no banco de dados.
     *
     * @param createModelDto O DTO contendo os dados do modelo a ser criado.
     */
    @Override
    public void create(CreateModelDto createModelDto) {
        productRepository.findById(createModelDto.getProduct().getId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));

        Model model = modelMapper.map(createModelDto, Model.class);
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
        Model model = modelRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
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