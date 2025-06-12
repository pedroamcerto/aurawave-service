package com.aurawave.controller;

import com.aurawave.dto.laboratory.CreateLaboratoryDto;
import com.aurawave.dto.laboratory.GetLaboratoryDto;
import com.aurawave.service.LaboratoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controladora responsável pela gestão dos laboratórios (unidades laboratoriais).
 */
@RestController
@RequestMapping("/api/laboratories")
public class LaboratoryController {

    @Autowired
    private LaboratoryService laboratoryService;

    /**
     * Cria um novo laboratório.
     *
     * @param createLaboratoryDto DTO com os dados do laboratório a ser criado.
     * @return Status HTTP indicando que o laboratório foi criado.
     */
    @PostMapping
    public ResponseEntity<Void> createLaboratory(@Valid @RequestBody CreateLaboratoryDto createLaboratoryDto) {
        laboratoryService.create(createLaboratoryDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Recupera um laboratório pelo seu ID.
     *
     * @param id ID do laboratório a ser recuperado.
     * @return O DTO do laboratório com o ID especificado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetLaboratoryDto> getLaboratoryById(@PathVariable Long id) {
        GetLaboratoryDto laboratoryDto = laboratoryService.getById(id);
        return new ResponseEntity<>(laboratoryDto, HttpStatus.OK);
    }

    /**
     * Recupera todos os laboratórios cadastrados no sistema.
     *
     * @return Lista de DTOs com todos os laboratórios.
     */
    @GetMapping
    public ResponseEntity<List<GetLaboratoryDto>> getAllLaboratories() {
        List<GetLaboratoryDto> laboratories = laboratoryService.getAll();
        return new ResponseEntity<>(laboratories, HttpStatus.OK);
    }

    /**
     * Deleta um laboratório pelo seu ID.
     * Se o laboratório for deletado, os almoxarifados associados a ele serão deletados também.
     *
     * @param id ID do laboratório a ser deletado.
     * @return Status HTTP indicando que o laboratório foi deletado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaboratory(@PathVariable Long id) {
        laboratoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
