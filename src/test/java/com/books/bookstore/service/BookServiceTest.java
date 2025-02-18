package com.books.bookstore.service;

import com.books.bookstore.model.AppUser;
import com.books.bookstore.model.Book;
import com.books.bookstore.model.Booking;
import com.books.bookstore.repository.BookRepository;
import com.books.bookstore.repository.BookingRepository;
import com.books.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = List.of(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById() {
        Book book = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.saveBook(book);

        assertEquals(book, result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCreateBooking() {
        AppUser user = new AppUser();
        user.setId(1L);
        Book book = new Book();
        book.setId(1L);
        book.setBooked(false);
        List<Book> books = List.of(book);
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBooks(books);
        booking.setStatus("BOOKED");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findAllById(List.of(1L))).thenReturn(books);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookService.createBooking(1L, List.of(1L));

        assertEquals("BOOKED", result.getStatus());
        assertEquals(1, result.getBooks().size());
        verify(userRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).findAllById(List.of(1L));
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testReturnBooks() {
        Booking booking = new Booking();
        Book book = new Book();
        book.setBooked(true);
        booking.setBooks(List.of(book));
        booking.setStatus("BOOKED");

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookService.returnBooks(1L);

        assertEquals("RETURNED", result.getStatus());
        assertFalse(result.getBooks().get(0).isBooked());
        verify(bookingRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testGetBookingsByUserId() {
        List<Booking> bookings = List.of(new Booking(), new Booking());
        when(bookingRepository.findByUserId(1L)).thenReturn(bookings);

        List<Booking> result = bookService.getBookingsByUserId(1L);

        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetBookingsByStatus() {
        List<Booking> bookings = List.of(new Booking(), new Booking());
        when(bookingRepository.findByStatus("BOOKED")).thenReturn(bookings);

        List<Booking> result = bookService.getBookingsByStatus("BOOKED");

        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findByStatus("BOOKED");
    }
}