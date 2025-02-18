package com.books.bookstore.service;

import com.books.bookstore.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.bookstore.model.Book;
import com.books.bookstore.model.Booking;
import com.books.bookstore.repository.BookRepository;
import com.books.bookstore.repository.BookingRepository;
import com.books.bookstore.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    
     @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

     // Create a new booking for a user
    @Transactional
    public Booking createBooking(Long userId, List<Long> bookIds) {
        Optional<AppUser> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        List<Book> books = bookRepository.findAllById(bookIds);
        books.stream().filter(Book::isBooked).findFirst().ifPresent(book -> {throw new IllegalArgumentException("Please select books which are not already booked");});
        books =  books.stream().map(book -> book.setBooked(true)).toList();

        Booking booking = new Booking();
        booking.setUser(userOpt.get());
        booking.setBooks(books);
        booking.setStatus("BOOKED");

        return bookingRepository.save(booking);
    }

    // Return books for a booking
    @Transactional
    public Booking returnBooks(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking not found");
        }
        Booking booking = bookingOpt.get();
        booking.setBooks(booking.getBooks().stream().map(book -> book.setBooked(false)).collect(Collectors.toCollection(ArrayList::new)));
        booking.setStatus("RETURNED");

        return bookingRepository.save(booking);
    }


    @Transactional
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Transactional
    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(status);
    }


}
