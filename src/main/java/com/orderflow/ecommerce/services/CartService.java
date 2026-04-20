package com.orderflow.ecommerce.services;

import com.orderflow.ecommerce.dtos.AddItemRequest;
import com.orderflow.ecommerce.dtos.CartItemResponse;
import com.orderflow.ecommerce.entities.Cart;
import com.orderflow.ecommerce.entities.CartItem;
import com.orderflow.ecommerce.repositories.CartItemRepository;
import com.orderflow.ecommerce.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public CartItemResponse addItemToCart(AddItemRequest request) {
        Cart cart = cartRepository.findByUserId(1L)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(1L);

                    return cartRepository.saveAndFlush(newCart);
                });

        CartItem item = new CartItem();
        item.setQuantity(request.quantity());
        item.setPrice(request.price());
        item.setCart(cart);
        item.setProductId(request.productId());

        CartItem savedItem = cartItemRepository.saveAndFlush(item);
        return CartItemResponse.from(savedItem);
    }

    @Transactional
    public CartItemResponse updateItem(Long itemId, AddItemRequest request) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        item.setQuantity(request.quantity());
        item.setPrice(request.price());
        item.setProductId(request.productId());

        CartItem updatedItem = cartItemRepository.saveAndFlush(item);
        return CartItemResponse.from(updatedItem);
    }

    @Transactional
    public void clearCurrentCart() {
        cartRepository.deleteByUserId(1L);

        cartRepository.flush();
    }
}