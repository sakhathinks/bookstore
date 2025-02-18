package com.books.bookstore.model;

import com.books.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserFunctionalTest {

    @Autowired
    private UserRepository userRepository;

    private AppUser user;

    @BeforeEach
    void setUp() {
        user = new AppUser();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user = userRepository.save(user);
    }

    @Test
    @Transactional
    void testCreateUser() {
        AppUser newUser = new AppUser();
        newUser.setUsername("newuser");
        newUser.setEmail("newuser@example.com");

        AppUser result = userRepository.save(newUser);

        assertNotNull(result);
        assertEquals("newuser", result.getUsername());
    }

    @Test
    @Transactional
    void testGetAllUsers() {
        Iterable<AppUser> users = userRepository.findAll();

        assertNotNull(users);
        assertTrue(users.iterator().hasNext());
    }
}