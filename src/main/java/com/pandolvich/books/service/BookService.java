package com.pandolvich.books.service;

import com.pandolvich.books.model.Book;
import com.pandolvich.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    @Cacheable("books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public void  searchImageOnTheInternet(String title) {
        // search for the book cover on the internet
        
            
    }
    

}
