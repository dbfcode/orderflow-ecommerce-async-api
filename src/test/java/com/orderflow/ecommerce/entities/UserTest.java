package com.orderflow.ecommerce.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void shouldCreateUserCorrectly() {
        User user = new User(null, "Giovanna", "gi@email.com", "123456");

        assertEquals("Giovanna", user.getName());
        assertEquals("gi@email.com", user.getEmail());
    }
}