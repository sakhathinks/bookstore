package com.books.bookstore.controller;

import com.books.bookstore.model.Booking;
import com.books.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingsControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookingsController bookingsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking() {
        Booking booking = new Booking();
        when(bookService.createBooking(1L, List.of(1L))).thenReturn(booking);

        Booking result = bookingsController.createBooking(List.of(1L), 1L);

        assertEquals(booking, result);
        verify(bookService, times(1)).createBooking(1L, List.of(1L));
    }

    @Test
    void testReturnBooking() {
        Booking booking = new Booking();
        when(bookService.returnBooks(1L)).thenReturn(booking);

        Booking result = bookingsController.returnBooking(1L);

        assertEquals(booking, result);
        verify(bookService, times(1)).returnBooks(1L);
    }

    @Test
    void testGetBookings() {
        List<Booking> bookings = List.of(new Booking(), new Booking());
        when(bookService.getBookingsByUserId(1L)).thenReturn(bookings);

        List<Booking> result = bookingsController.getBookings(1L);

        assertEquals(2, result.size());
        verify(bookService, times(1)).getBookingsByUserId(1L);
    }
}
