package com.books.bookstore.model;

import com.books.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookFunctionalTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setDescription("Test Description");
        book.setPrice(10.0);
        book = bookRepository.save(book);
    }

    @Test
    @Transactional
    void testCreateBook() {
        Book newBook = new Book();
        newBook.setTitle("New Book");
        newBook.setAuthor("New Author");
        newBook.setDescription("New Description");
        newBook.setPrice(15.0);

        Book result = bookRepository.save(newBook);

        assertNotNull(result);
        assertEquals("New Book", result.getTitle());
    }

    @Test
    @Transactional
    void testGetAllBooks() {
        Iterable<Book> books = bookRepository.findAll();

        assertNotNull(books);
        assertTrue(books.iterator().hasNext());
    }
}