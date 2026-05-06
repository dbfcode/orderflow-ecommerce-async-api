package com.orderflow.ecommerce.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryTest {

    @Test
    void shouldCreateCategoryWithCorrectName() {
        Category cat = new Category(null, "Eletrônicos");

        assertNotNull(cat);
        assertEquals("Eletrônicos", cat.getName());
    }

    @Test
    void shouldUpdateCategoryName() {
        Category cat = new Category(1L, "Livros");
        cat.setName("Games");

        assertEquals("Games", cat.getName());
    }
        @Test
        void shouldCreateCategoryWithNullId() {
            Category cat = new Category();
            cat.setName("Moda");

            assertNotNull(cat);
            assertEquals("Moda", cat.getName());
        }

        @Test
        void shouldHandleEmptyCategory() {
            Category cat = new Category();
            assertEquals(null, cat.getName());
        }
    }

