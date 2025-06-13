package com.aurawave.controller;

import com.aurawave.dto.product.CreateProductDto;
import com.aurawave.dto.product.GetProductDto;
import com.aurawave.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Cria um novo produto.
     *
     * @param createProductDto O DTO contendo os dados do produto a ser criado.
     * @return Uma resposta HTTP indicando o sucesso da criação do produto.
     */
    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        productService.create(createProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Atualiza um produto existente.
     *
     * @param id O ID do produto a ser atualizado.
     * @param createProductDto O DTO com os dados atualizados do produto.
     * @return Uma resposta HTTP indicando o sucesso da atualização.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @Valid @RequestBody CreateProductDto createProductDto) {
        productService.update(id, createProductDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Recupera um produto pelo seu ID.
     *
     * @param id O ID do produto a ser recuperado.
     * @return Uma resposta HTTP com os dados do produto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetProductDto> getProductById(@PathVariable Long id) {
        GetProductDto getProductDto = productService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(getProductDto);
    }

    /**
     * Recupera todos os produtos cadastrados.
     *
     * @return Uma resposta HTTP com a lista de todos os produtos.
     */
    @GetMapping
    public ResponseEntity<List<GetProductDto>> getAllProducts() {
        List<GetProductDto> products = productService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

}
