package com.books.bookstore.model;

import com.books.bookstore.repository.BookingRepository;
import com.books.bookstore.repository.UserRepository;
import com.books.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingFunctionalTest {

    @Autowired
    private BookingRepository bookingRepository;

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
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBooks(List.of(book));
        booking.setStatus("BOOKED");

        Booking result = bookingRepository.save(booking);

        assertNotNull(result);
        assertEquals("BOOKED", result.getStatus());
        assertEquals(1, result.getBooks().size());
    }

    @Test
    @Transactional
    void testGetAllBookings() {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBooks(List.of(book));
        booking.setStatus("BOOKED");
        bookingRepository.save(booking);

        Iterable<Booking> bookings = bookingRepository.findAll();

        assertNotNull(bookings);
        assertTrue(bookings.iterator().hasNext());
    }
}