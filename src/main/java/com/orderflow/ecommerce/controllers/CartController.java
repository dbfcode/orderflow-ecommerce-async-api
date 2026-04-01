package com.orderflow.ecommerce.controllers;

import com.orderflow.ecommerce.entities.CartItem;
import com.orderflow.ecommerce.repositories.CartItemRepository;
import com.orderflow.ecommerce.repositories.CartRepository;
import com.orderflow.ecommerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository repository;

    @GetMapping
    public List<CartItem> listCart() {
        return repository.findAll();
    }

    @PostMapping
    public CartItem addItem(@RequestBody CartItem item) {
        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public void removeItem(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public CartItem updateItem(@PathVariable Long id, @RequestBody CartItem itemDetails) {
        CartItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado com id: " + id));

        item.setQuantity(itemDetails.getQuantity());
        return repository.save(item);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCartByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
