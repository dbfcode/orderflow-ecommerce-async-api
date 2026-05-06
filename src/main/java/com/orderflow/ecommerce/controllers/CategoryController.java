package com.orderflow.ecommerce.controllers;

import com.orderflow.ecommerce.dtos.ErrorResponse;
import com.orderflow.ecommerce.entities.Category;
import com.orderflow.ecommerce.repositories.CategoryRepository;
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
@RequestMapping(value = "/categories")
@Tag(name = "Categorias", description = "CRUD de categorias de produtos")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @GetMapping
    @Operation(summary = "Lista todas as categorias")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Category.class))))
    })
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Obtém categoria por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                    content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Categoria inexistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Category> findById(
            @Parameter(description = "Identificador numérico da categoria", required = true, example = "1")
            @PathVariable Long id) {
        Category obj = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoria não encontrada com o ID: " + id));
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @Operation(summary = "Cria uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria criada e persistida",
                    content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "400", description = "Corpo inválido ou falha de validação",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Category> insert(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da categoria (campo id pode ser omitido)", required = true)
            @Valid @RequestBody Category obj) {
        return ResponseEntity.ok().body(repository.save(obj));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Remove categoria por id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Exclusão processada (idempotente se o registro não existir)")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identificador da categoria a remover", required = true, example = "1")
            @PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualiza o nome de uma categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada",
                    content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "400", description = "Corpo inválido ou falha de validação",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria inexistente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Category> update(
            @Parameter(description = "Identificador da categoria", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Novos dados (normalmente apenas name)", required = true)
            @Valid @RequestBody Category obj) {
        Category entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoria não encontrada para atualizar"));

        entity.setName(obj.getName());
        return ResponseEntity.ok().body(repository.save(entity));
    }
}
