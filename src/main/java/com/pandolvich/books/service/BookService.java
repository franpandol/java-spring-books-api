package com.pandolvich.books.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.pandolvich.books.model.Book;
import com.pandolvich.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

@Service
public class BookService {

	@Value("${spring.cloud.azure.storage.blob.connection-string}")
	private String connectionString;

	@Value("${spring.cloud.azure.storage.blob.container-name}")
	private String containerName;

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

	public String searchImageOnTheInternet(String title) {
		return "https://www.google.com/search?q=" + title + "&tbm=isch";

	}

	public String uploadImage(MultipartFile imageFile) throws IOException {
		BlobContainerClient container = new BlobContainerClientBuilder().connectionString(connectionString)
			.containerName(containerName)
			.buildClient();

		BlobClient blobClient = container.getBlobClient(imageFile.getOriginalFilename());
		blobClient.upload(imageFile.getInputStream(), imageFile.getSize());
		return blobClient.getBlobUrl();

	}

	public Book saveImageUrl(Long bookId, MultipartFile imageFile) {
		String imageUrl;
		try {
			imageUrl = uploadImage(imageFile);
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Book book = bookRepository.findById(bookId).orElse(null);
		book.setImage(imageUrl);
		return bookRepository.save(book);
	}

}
