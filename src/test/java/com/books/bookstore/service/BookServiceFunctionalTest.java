package com.books.bookstore.service;

import com.books.bookstore.model.AppUser;
import com.books.bookstore.model.Book;
import com.books.bookstore.model.Booking;
import com.books.bookstore.repository.BookRepository;
import com.books.bookstore.repository.BookingRepository;
import com.books.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceFunctionalTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

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
        Booking booking = bookService.createBooking(user.getId(), List.of(book.getId()));

        assertNotNull(booking);
        assertEquals("BOOKED", booking.getStatus());
        assertEquals(1, booking.getBooks().size());
        assertTrue(booking.getBooks().get(0).isBooked());
    }

    @Test
    @Transactional
    void testReturnBooks() {
        Booking booking = bookService.createBooking(user.getId(), List.of(book.getId()));
        Booking returnedBooking = bookService.returnBooks(booking.getId());

        assertNotNull(returnedBooking);
        assertEquals("RETURNED", returnedBooking.getStatus());
        assertFalse(returnedBooking.getBooks().get(0).isBooked());
    }

    @Test
    @Transactional
    void testGetBookingsByUserId() {
        bookService.createBooking(user.getId(), List.of(book.getId()));
        List<Booking> bookings = bookService.getBookingsByUserId(user.getId());

        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    @Transactional
    void testGetBookingsByStatus() {
        bookService.createBooking(user.getId(), List.of(book.getId()));
        List<Booking> bookings = bookService.getBookingsByStatus("BOOKED");

        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }
}