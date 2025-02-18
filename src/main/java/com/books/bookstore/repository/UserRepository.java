package com.books.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.bookstore.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
}

