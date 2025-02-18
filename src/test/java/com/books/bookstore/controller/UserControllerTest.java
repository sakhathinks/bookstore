package com.books.bookstore.controller;

import com.books.bookstore.model.AppUser;
import com.books.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        AppUser user = new AppUser();
        when(userRepository.save(user)).thenReturn(user);

        AppUser result = userController.createUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {
        List<AppUser> users = List.of(new AppUser(), new AppUser());
        when(userRepository.findAll()).thenReturn(users);

        Iterable<AppUser> result = userController.getAllUsers();

        assertEquals(2, ((List<AppUser>) result).size());
        verify(userRepository, times(1)).findAll();
    }
}