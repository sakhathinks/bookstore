 package com.books.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.books.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    
}