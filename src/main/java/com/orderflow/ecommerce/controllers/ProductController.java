package com.orderflow.ecommerce.controllers;

import com.orderflow.ecommerce.dtos.ErrorResponse;
import com.orderflow.ecommerce.entities.Product;
import com.orderflow.ecommerce.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/products")
@Tag(name = "Produtos", description = "CRUD de produtos do catálogo")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    @Operation(summary = "Lista todos os produtos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class))))
    })
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Obtém produto por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Produto inexistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Product> findById(
            @Parameter(description = "Identificador numérico do produto", required = true, example = "10")
            @PathVariable Long id) {
        Product obj = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado"));
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @Operation(summary = "Cria um produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto criado e persistido",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Corpo inválido ou falha de validação",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Product> insert(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do produto; id pode ser omitido; category pode ser {\"id\": n}", required = true)
            @Valid @RequestBody Product obj) {
        return ResponseEntity.ok().body(repository.save(obj));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Remove produto por id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Exclusão processada (idempotente se o registro não existir)")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identificador do produto a remover", required = true, example = "10")
            @PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualiza um produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Corpo inválido ou falha de validação",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produto inexistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Product> update(
            @Parameter(description = "Identificador do produto", required = true, example = "10")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos a atualizar; category pode ser referência por id", required = true)
            @Valid @RequestBody Product obj) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado para atualizar"));

        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
        entity.setPrice(obj.getPrice());
        entity.setStockQuantity(obj.getStockQuantity());
        entity.setCategory(obj.getCategory());

        return ResponseEntity.ok().body(repository.save(entity));
    }
}
