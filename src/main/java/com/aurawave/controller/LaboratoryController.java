package com.aurawave.controller;

import com.aurawave.dto.laboratory.CreateLaboratoryDto;
import com.aurawave.service.LaboratoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controladora responsável pela gestão dos laboratórios (unidades laboratoriais).
 *
 */

@RestController
@RequestMapping("/api/laboratory")
public class LaboratoryController {


    @Autowired
    private LaboratoryService laboratoryService;


    @PostMapping
    public ResponseEntity<Void> createLaboratory(@Valid @RequestBody CreateLaboratoryDto createLaboratoryDto) {
        laboratoryService.create(createLaboratoryDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
