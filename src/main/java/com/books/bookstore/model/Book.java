package com.books.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean isBooked;
    
    @NotEmpty(message = "Title is required")
    @Size(min = 2, message = "Title must be at least 2 characters long")
    private String title;
    
    @NotEmpty(message = "Author is required")
    private String author;
    
    private String description;
    
    private double price;
    
    // Getters and Setters
    public boolean isBooked() {
        return isBooked;
    }
    public Book setBooked(boolean isBooked) {
        this.isBooked = isBooked;
        return this;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
