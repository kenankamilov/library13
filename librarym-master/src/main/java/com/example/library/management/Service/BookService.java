package com.example.library.management.Service;

import com.example.library.management.Model.Book;
import com.example.library.management.Repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Create a new book
    public void createBook(Book book) {
        bookRepository.save(book);
    }

    // Get book by ID
    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(() -> new RuntimeException("Kitab tapılmadı: " + id));
    }

    // Update book
    public void updateBook(Long id, Book updatedBook) {
        Book existingBook = getBookById(id);  // This will throw an exception if not found
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setStock(updatedBook.getStock());
        existingBook.setImage(updatedBook.getImage());
        existingBook.setDescription(updatedBook.getDescription());
        bookRepository.save(existingBook);
    }

    // Delete book
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
}
