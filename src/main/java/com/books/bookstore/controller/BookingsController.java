package com.books.bookstore.controller;

import com.books.bookstore.model.Booking;
import com.books.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingsController {

    @Autowired
    private BookService bookingservice;

    @PostMapping
    public Booking createBooking(@RequestBody List<Long> bookIds, @RequestParam Long userId) {
//        System.out.println("got this " + userId + "  bookids= " + bookIds);
        return bookingservice.createBooking(userId, bookIds);
    }

    @PostMapping("/return")
    public Booking returnBooking(@RequestParam Long bookingId) {
        return bookingservice.returnBooks(bookingId);
    }

    @GetMapping
    public List<Booking> getBookings( @RequestParam Long userId) {
        return bookingservice.getBookingsByUserId(userId);
    }

    

}
