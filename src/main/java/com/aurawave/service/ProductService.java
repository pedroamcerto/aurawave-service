package com.aurawave.service;

import com.aurawave.core.exception.NotFoundException;
import com.aurawave.domain.interfaces.ServiceInterface;
import com.aurawave.domain.model.Product;
import com.aurawave.dto.product.CreateProductDto;
import com.aurawave.dto.product.GetProductDto;
import com.aurawave.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsável pelas operações CRUD para a entidade Product.
 * Implementa a interface {@link ServiceInterface} para fornecer as operações de criação,
 * atualização, remoção e busca de produtos.
 */
@Service
@RequiredArgsConstructor
public class ProductService implements ServiceInterface<GetProductDto, CreateProductDto> {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    private static final String NOT_FOUND_MESSAGE = "Produto não encontrado";

    /**
     * Cria um novo produto.
     *
     * Este método mapeia o DTO de entrada {@link CreateProductDto} para a entidade Product
     * e persiste o novo produto no banco de dados.
     *
     * @param entity O DTO contendo os dados do produto a ser criado.
     */
    @Override
    public void create(CreateProductDto entity) {
        Product product = modelMapper.map(entity, Product.class);
        productRepository.save(product);
    }

    /**
     * Atualiza um produto existente.
     *
     * Este método busca o produto por ID, atualiza seus dados e salva as mudanças no banco de dados.
     *
     * @param id O ID do produto a ser atualizado.
     * @param createProductDto O DTO com os dados atualizados do produto.
     */
    @Override
    public void update(Long id, CreateProductDto createProductDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));

        modelMapper.map(createProductDto, product);
        productRepository.save(product);
    }

    /**
     * Recupera um produto pelo seu ID.
     *
     * Este método busca um produto no banco de dados e retorna um DTO com seus dados.
     *
     * @param id O ID do produto a ser recuperado.
     * @return O DTO {@link GetProductDto} com os dados do produto encontrado.
     */
    @Override
    public GetProductDto getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));

        return modelMapper.map(product, GetProductDto.class);
    }

    /**
     * Recupera todos os produtos cadastrados no banco de dados.
     *
     * Este método retorna todos os produtos armazenados no banco de dados como uma lista de DTOs.
     *
     * @return Uma lista de DTOs {@link GetProductDto} com os dados de todos os produtos.
     */
    @Override
    public List<GetProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, GetProductDto.class))
                .collect(Collectors.toList());
    }

}