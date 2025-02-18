package com.books.bookstore.controller;

import com.books.bookstore.model.AppUser;
import com.books.bookstore.repository.UserRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @PostMapping
    public AppUser createUser(@Valid @RequestBody AppUser user) {
        return userRepository.save(user);
    }

    @GetMapping
    public Iterable<AppUser> getAllUsers() {
        return userRepository.findAll();
    }
}
