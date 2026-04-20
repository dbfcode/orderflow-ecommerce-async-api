package com.orderflow.ecommerce.repositories;

import com.orderflow.ecommerce.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // <--- Importante ter esse import!

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
