package com.books.bookstore.controller;

import com.books.bookstore.model.Book;
import com.books.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAvailableBooks() {
        List<Book> books = List.of(new Book(), new Book());
        when(bookService.getAllBooks()).thenReturn(books);

        List<Book> result = bookController.getAllAvailableBooks();

        assertEquals(2, result.size());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById() {
        Book book = new Book();
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        when(bookService.saveBook(book)).thenReturn(book);

        Book result = bookController.addBook(book);

        assertEquals(book, result);
        verify(bookService, times(1)).saveBook(book);
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(bookService.saveBook(book)).thenReturn(book);

        ResponseEntity<Book> response = bookController.updateBook(1L, book);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
        verify(bookService, times(1)).getBookById(1L);
        verify(bookService, times(1)).saveBook(book);
    }

    @Test
    void testDeleteBook() {
        Book book = new Book();
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookService).deleteBook(1L);

        ResponseEntity<Void> response = bookController.deleteBook(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(bookService, times(1)).getBookById(1L);
        verify(bookService, times(1)).deleteBook(1L);
    }
}
