package com.books.bookstore.controller;

import com.books.bookstore.model.AppUser;
import com.books.bookstore.model.Book;
import com.books.bookstore.model.Booking;
import com.books.bookstore.service.BookService;
import com.books.bookstore.repository.BookRepository;
import com.books.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingsControllerFunctionalTest {

    @Autowired
    private BookingsController bookingsController;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    private AppUser user;
    private Book book;

    @BeforeEach
    void setUp() {
        user = new AppUser();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user = userRepository.save(user);

        book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setDescription("Test Description");
        book.setPrice(10.0);
        book = bookRepository.save(book);
    }

    @Test
    @Transactional
    void testCreateBooking() {
        Booking booking = bookingsController.createBooking(List.of(book.getId()), user.getId());

        assertNotNull(booking);
        assertEquals("BOOKED", booking.getStatus());
        assertEquals(1, booking.getBooks().size());
        assertTrue(booking.getBooks().get(0).isBooked());
    }

    @Test
    @Transactional
    void testReturnBooking() {
        Booking booking = bookingsController.createBooking(List.of(book.getId()), user.getId());
        Booking returnedBooking = bookingsController.returnBooking(booking.getId());

        assertNotNull(returnedBooking);
        assertEquals("RETURNED", returnedBooking.getStatus());
        assertFalse(returnedBooking.getBooks().get(0).isBooked());
    }

    @Test
    @Transactional
    void testGetBookings() {
        bookingsController.createBooking(List.of(book.getId()), user.getId());
        List<Booking> bookings = bookingsController.getBookings(user.getId());

        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }
}