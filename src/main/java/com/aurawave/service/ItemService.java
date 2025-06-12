package com.aurawave.service;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.enumerated.ItemStatus;
import com.aurawave.domain.interfaces.ServiceInterface;
import com.aurawave.domain.model.Item;
import com.aurawave.dto.item.CreateItemDto;
import com.aurawave.dto.item.GetItemDto;
import com.aurawave.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsável pelas operações CRUD para a entidade Item.
 *
 * Implementa a interface {@link ServiceInterface} para fornecer as operações de criação,
 * atualização, remoção e busca de itens. A classe {@link ItemService} interage com o repositório
 * {@link ItemRepository} para acessar e persistir dados dos itens.
 *
 * @see ItemRepository
 * @see ModelMapper
 */
@Service
public class ItemService implements ServiceInterface<GetItemDto, CreateItemDto> {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Cria um novo item.
     *
     * Este método mapeia o DTO de entrada {@link CreateItemDto} para a entidade Item
     * e persiste o novo item no banco de dados.
     *
     * @param createItemDto O DTO contendo os dados do item a ser criado.
     */
    @Override
    public void create(CreateItemDto createItemDto) {
        Item item = modelMapper.map(createItemDto, Item.class);

        item.setStatus(ItemStatus.OUT_OF_STOCK);
        itemRepository.save(item);
    }

    /**
     * Atualiza um item existente.
     *
     * Este método busca o item no banco de dados pelo ID, mapeia os dados do DTO
     * {@link CreateItemDto} e atualiza os dados do item no banco de dados.
     *
     * @param id O ID do item a ser atualizado.
     * @param createItemDto O DTO contendo os novos dados do item.
     * @throws NotFoundException Se o item com o ID fornecido não for encontrado.
     */
    @Override
    public void update(Long id, CreateItemDto createItemDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item não encontrado"));

        modelMapper.map(createItemDto, item);
        itemRepository.save(item);
    }

    /**
     * Atualiza o status de um item existente
     *
     * Este método busca o item no banco de dados pelo ID, mapeia os dados do DTO
     * {@link ItemStatus} e atualiza os dados do item no banco de dados.
     *
     * @param id O ID do item a ser atualizado.
     * @param status status de um item que ainda não está em um almoxarifado pode ser alterado.
     * @throws NotFoundException Se o item com o ID fornecido não for encontrado.
     */
    @Override
    public void update(Long id, ItemStatus status) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item não encontrado"));

        if (!status.equals(ItemStatus.DAMAGED) || !status.equals(ItemStatus.EXPIRED) || !status.equals(ItemStatus.DISCARDED)) {
            throw new IllegalArgumentException("Item não pode ser atualizado com esse status");
        }

        item.setStatus(status);
        itemRepository.save(item);
    }

    /**
     * Recupera um item pelo seu ID.
     *
     * Este método busca um item no banco de dados pelo ID e mapeia os dados encontrados
     * para um DTO de resposta {@link GetItemDto}.
     *
     * @param id O ID do item a ser recuperado.
     * @return O DTO {@link GetItemDto} contendo os dados do item encontrado.
     * @throws NotFoundException Se o item com o ID fornecido não for encontrado.
     */
    @Override
    public GetItemDto getById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item não encontrado"));
        return modelMapper.map(item, GetItemDto.class);
    }

    /**
     * Recupera todos os itens cadastrados no banco de dados.
     *
     * Este método busca todos os itens do banco de dados, mapeia cada um deles para o DTO
     * {@link GetItemDto} e retorna a lista.
     *
     * @return Uma lista de DTOs {@link GetItemDto} contendo os dados de todos os itens.
     */
    @Override
    public List<GetItemDto> getAll() {
        return itemRepository.findAll().stream()
                .map(item -> modelMapper.map(item, GetItemDto.class))
                .collect(Collectors.toList());
    }

}