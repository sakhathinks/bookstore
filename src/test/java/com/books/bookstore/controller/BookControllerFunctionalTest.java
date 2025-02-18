package com.books.bookstore.controller;

import com.books.bookstore.model.Book;
import com.books.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookControllerFunctionalTest {

    @Autowired
    private BookController bookController;

    @Autowired
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setDescription("Test Description");
        book.setPrice(10.0);
        book = bookService.saveBook(book);
    }

    @Test
    @Transactional
    void testGetAllAvailableBooks() {
        List<Book> books = bookController.getAllAvailableBooks();

        assertNotNull(books);
        assertFalse(books.isEmpty());
    }

    @Test
    @Transactional
    void testGetBookById() {
        ResponseEntity<Book> response = bookController.getBookById(book.getId());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
    }

    @Test
    @Transactional
    void testAddBook() {
        Book newBook = new Book();
        newBook.setTitle("New Book");
        newBook.setAuthor("New Author");
        newBook.setDescription("New Description");
        newBook.setPrice(15.0);

        Book result = bookController.addBook(newBook);

        assertNotNull(result);
        assertEquals("New Book", result.getTitle());
    }

    @Test
    @Transactional
    void testUpdateBook() {
        book.setTitle("Updated Title");
        ResponseEntity<Book> response = bookController.updateBook(book.getId(), book);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Title", response.getBody().getTitle());
    }

    @Test
    @Transactional
    void testDeleteBook() {
        ResponseEntity<Void> response = bookController.deleteBook(book.getId());

        assertEquals(204, response.getStatusCodeValue());
        Optional<Book> deletedBook = bookService.getBookById(book.getId());
        assertFalse(deletedBook.isPresent());
    }
}