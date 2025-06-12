package com.aurawave.controller;

import com.aurawave.dto.warehouse.CreateWarehouseDto;
import com.aurawave.dto.warehouse.GetWarehouseDto;
import com.aurawave.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controladora responsável pela gestão dos almoxarifados.
 */
@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    /**
     * Cria um novo almoxarifado.
     *
     * @param createWarehouseDto DTO com os dados do almoxarifado a ser criado.
     * @return Resposta HTTP com o status de criação.
     */
    @PostMapping
    public ResponseEntity<Void> createWarehouse(@Valid @RequestBody CreateWarehouseDto createWarehouseDto) {
        warehouseService.create(createWarehouseDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Recupera os dados de um almoxarifado pelo seu ID.
     *
     * @param id O ID do almoxarifado.
     * @return O DTO com os dados do almoxarifado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetWarehouseDto> getWarehouseById(@PathVariable Long id) {
        GetWarehouseDto warehouse = warehouseService.getById(id);
        return ResponseEntity.ok(warehouse);
    }

    /**
     * Recupera todos os almoxarifados cadastrados.
     *
     * @return Uma lista de DTOs com os dados de todos os almoxarifados.
     */
    @GetMapping
    public ResponseEntity<List<GetWarehouseDto>> getAllWarehouses() {
        List<GetWarehouseDto> warehouses = warehouseService.getAll();
        return ResponseEntity.ok(warehouses);
    }

    /**
     * Atualiza os dados de um almoxarifado.
     *
     * @param id O ID do almoxarifado a ser atualizado.
     * @param createWarehouseDto DTO com os dados atualizados do almoxarifado.
     * @return Resposta HTTP com o status de atualização.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWarehouse(@PathVariable Long id, @Valid @RequestBody CreateWarehouseDto createWarehouseDto) {
        warehouseService.update(id, createWarehouseDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deleta um almoxarifado pelo ID.
     *
     * @param id O ID do almoxarifado a ser deletado.
     * @return Resposta HTTP com o status de exclusão.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        warehouseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Adiciona um item ao almoxarifado.
     *
     * @param warehouseId O ID do almoxarifado.
     * @param itemId O ID do item a ser adicionado.
     * @return Resposta HTTP com o status de adição.
     */
    @PostMapping("/{warehouseId}/items/{itemId}")
    public ResponseEntity<Void> addItemToWarehouse(@PathVariable Long warehouseId, @PathVariable Long itemId) {
        warehouseService.addItemToWarehouse(warehouseId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Remove um item do almoxarifado.
     *
     * @param warehouseId O ID do almoxarifado.
     * @param itemId O ID do item a ser removido.
     * @return Resposta HTTP com o status de remoção.
     */
    @DeleteMapping("/{warehouseId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromWarehouse(@PathVariable Long warehouseId, @PathVariable Long itemId) {
        warehouseService.removeItemFromWarehouse(warehouseId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
