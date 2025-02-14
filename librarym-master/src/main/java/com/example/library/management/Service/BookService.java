package com.example.library.management.Service;


import com.example.library.management.Model.Book;
import com.example.library.management.Model.Category;
import com.example.library.management.Repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book getBookDetails(Long id) {
        return getBookById(id).orElseThrow(() -> new IllegalArgumentException("Kitab tapılmadı: " + id));
    }

    public Page<Book> getBooksByCategory(Category category, Pageable pageable) {
        return bookRepository.findByCategory(category, pageable);
    }

    public List<Book> getBooksByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }

    public void updateBook(Long id, Book book) {
        Book existingBook = getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kitab tapılmadı: " + id));

        existingBook.setTitle(book.getTitle());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setCategory(book.getCategory());
        existingBook.setStock(book.getStock());

        bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public void decreaseStock(Long bookId) {
        Book book = getBookById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Kitab tapılmadı: " + bookId));

        book.decreaseStock();
        bookRepository.save(book);
    }

    public void increaseStock(Long bookId) {
        Book book = getBookById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Kitab tapılmadı: " + bookId));

        book.increaseStock();
        bookRepository.save(book);
    }
}
