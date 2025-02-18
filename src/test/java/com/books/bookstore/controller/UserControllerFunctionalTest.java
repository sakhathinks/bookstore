package com.books.bookstore.controller;

import com.books.bookstore.model.AppUser;
import com.books.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerFunctionalTest {

    @Autowired
    private UserController userController;

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

        AppUser result = userController.createUser(newUser);

        assertNotNull(result);
        assertEquals("newuser", result.getUsername());
    }

    @Test
    @Transactional
    void testGetAllUsers() {
        List<AppUser> users = (List<AppUser>) userController.getAllUsers();

        assertNotNull(users);
        assertFalse(users.isEmpty());
    }
}