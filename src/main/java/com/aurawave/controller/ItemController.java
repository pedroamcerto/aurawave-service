package com.aurawave.controller;

import com.aurawave.dto.item.CreateItemDto;
import com.aurawave.dto.item.GetItemDto;
import com.aurawave.domain.enumerated.ItemStatus;
import com.aurawave.dto.item.UpdateItemStatusDto;
import com.aurawave.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * Cria um novo item.
     *
     * @param createItemDto O DTO contendo os dados do item a ser criado.
     * @return Uma resposta HTTP indicando o sucesso da criação do item.
     */
    @PostMapping
    public ResponseEntity<Void> createItem(@Valid @RequestBody CreateItemDto createItemDto) {
        itemService.create(createItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Atualiza um item existente.
     *
     * @param id O ID do item a ser atualizado.
     * @param createItemDto O DTO contendo os novos dados do item.
     * @return Uma resposta HTTP indicando o sucesso da atualização do item.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable Long id, @RequestBody CreateItemDto createItemDto) {
        itemService.update(id, createItemDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Atualiza o status de um item existente.
     *
     * @param id O ID do item a ser atualizado.
     * @param updateItemStatusDto O novo status do item.
     * @return Uma resposta HTTP indicando o sucesso da atualização do status do item.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateItemStatus(@PathVariable Long id, @RequestBody UpdateItemStatusDto updateItemStatusDto) {
        itemService.update(id, updateItemStatusDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Recupera um item pelo seu ID.
     *
     * @param id O ID do item a ser recuperado.
     * @return Uma resposta HTTP com os dados do item encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetItemDto> getItemById(@PathVariable Long id) {
        GetItemDto getItemDto = itemService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(getItemDto);
    }

    /**
     * Recupera todos os itens cadastrados.
     *
     * @return Uma resposta HTTP com a lista de todos os itens.
     */
    @GetMapping
    public ResponseEntity<List<GetItemDto>> getAllItems() {
        List<GetItemDto> items = itemService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
}