package com.orderflow.ecommerce.services;

import com.orderflow.ecommerce.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public void clearCartByUserId(Long userId) {
        cartItemRepository.deleteByCartUserId(userId);
    }
}