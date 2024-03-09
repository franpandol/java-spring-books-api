package com.pandolvich.books.controller;

import com.pandolvich.books.model.Book;
import com.pandolvich.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/books/")
public class BookController {

	private BookService bookService = null;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@PostMapping
	public Book saveBook(@RequestBody Book book) {
		return bookService.saveBook(book);
	}

	@GetMapping(params = "title", path = "/search")
	public List<Book> findByTitle(@RequestParam String title) {
		return bookService.findByTitle(title);
	}

	@PostMapping(path = "/upload", consumes = "multipart/form-data")
	public Book uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("book_id") Long bookId) {
		return bookService.saveImageUrl(bookId, file);
	}

}
