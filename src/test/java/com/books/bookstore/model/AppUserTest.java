package com.books.bookstore.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

    @Test
    void testAppUser() {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("testuser@example.com", user.getEmail());
    }
}