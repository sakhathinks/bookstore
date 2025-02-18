package com.books.bookstore.controller;

import org.springframework.web.bind.annotation.RestController;

import com.books.bookstore.model.Book;
import com.books.bookstore.service.BookService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    
    @GetMapping
    public List<Book> getAllAvailableBooks() {
        return bookService.getAllBooks().stream().filter(book -> !book.isBooked()).toList();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @PostMapping
    public Book addBook(@Valid @RequestBody Book book) {
        return bookService.saveBook(book);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        Optional<Book> existingBook = bookService.getBookById(id);
        if (existingBook.isPresent()) {
            book.setId(id);
            Book updatedBook = bookService.saveBook(book);
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
