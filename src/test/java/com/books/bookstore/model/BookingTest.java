package com.books.bookstore.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void testBooking() {
        AppUser user = new AppUser();
        Book book = new Book();
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUser(user);
        booking.setBooks(List.of(book));
        booking.setStatus("BOOKED");

        assertEquals(1L, booking.getId());
        assertEquals(user, booking.getUser());
        assertEquals(1, booking.getBooks().size());
        assertEquals("BOOKED", booking.getStatus());
    }
}