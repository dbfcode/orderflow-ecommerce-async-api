package com.orderflow.ecommerce.controllers;

import com.orderflow.ecommerce.dtos.AddItemRequest;
import com.orderflow.ecommerce.dtos.CartItemResponse;
import com.orderflow.ecommerce.dtos.UpdateQuantityRequest;
import com.orderflow.ecommerce.entities.Cart;
import com.orderflow.ecommerce.entities.CartItem;
import com.orderflow.ecommerce.repositories.CartItemRepository;
import com.orderflow.ecommerce.repositories.CartRepository;
import com.orderflow.ecommerce.services.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository repository;

    @Autowired
    private CartItemRepository itemRepository;

    @GetMapping
    public Optional<Cart> listCart() {
        return repository.findByUserId(1L);
    }

    @PostMapping
    public CartItemResponse addItem(@Valid @RequestBody AddItemRequest request) {
        return cartService.addItemToCart(request);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/items/{id}")
    public CartItemResponse updateItem(@PathVariable Long id, @Valid @RequestBody UpdateQuantityRequest request) {

        CartItem item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found with id: " + id));

        item.setQuantity(request.quantity());

        return CartItemResponse.from(itemRepository.save(item));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCurrentCart();
        return ResponseEntity.noContent().build();
    }
}
