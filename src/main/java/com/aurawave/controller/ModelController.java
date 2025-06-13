package com.aurawave.controller;

import com.aurawave.dto.model.CreateModelDto;
import com.aurawave.dto.model.GetModelDto;
import com.aurawave.service.ModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/model")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    /**
     * Cria um novo modelo.
     *
     * @param createModelDto O DTO contendo os dados do modelo a ser criado.
     * @return Uma resposta HTTP indicando o sucesso da criação do modelo.
     */
    @PostMapping
    public ResponseEntity<Void> createModel(@Valid @RequestBody CreateModelDto createModelDto) {
        modelService.create(createModelDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Recupera um modelo pelo seu ID.
     *
     * @param id O ID do modelo a ser recuperado.
     * @return Uma resposta HTTP com os dados do modelo.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetModelDto> getModelById(@PathVariable Long id) {
        GetModelDto getModelDto = modelService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(getModelDto);
    }

    /**
     * Recupera todos os modelos cadastrados.
     *
     * @return Uma resposta HTTP com a lista de todos os modelos.
     */
    @GetMapping
    public ResponseEntity<List<GetModelDto>> getAllModels() {
        List<GetModelDto> models = modelService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(models);
    }
}