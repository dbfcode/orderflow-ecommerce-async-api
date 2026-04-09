package com.orderflow.ecommerce.controllers;

import com.orderflow.ecommerce.entities.Category;
import com.orderflow.ecommerce.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category obj = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoria não encontrada com o ID: " + id));
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Category> insert(@Valid @RequestBody Category obj) {
        return ResponseEntity.ok().body(repository.save(obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody Category obj) {
        Category entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoria não encontrada para atualizar"));

        entity.setName(obj.getName());
        return ResponseEntity.ok().body(repository.save(entity));
    }
}