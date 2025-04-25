package com.example.library.management;

import com.example.library.management.Model.Book;
import com.example.library.management.Model.Author;
import com.example.library.management.Model.Category;
import com.example.library.management.Repository.BookRepository;
import com.example.library.management.Service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Author author;
    private Category category;

    @BeforeEach
    void setUp() {
        // Creating Author and Category to associate with Book
        author = new Author();
        author.setId(1L);
        author.setName("Orxan Hüseynov");

        category = new Category();
        category.setId(1L);
        category.setName("Elm");

        // Creating Book
        book = new Book();
        book.setId(1L);
        book.setIsbn(97831);
        book.setTitle("Kitab 1");
        book.setAuthor(author);
        book.setCategory(category);
        book.setStock(10);
        book.setImage("book1.jpg");
        book.setDescription("A description of Kitab 1");
    }

    @Test
    void testGetAllBooks() {
        // Mocking findAll to return a list of books
        Book book2 = new Book();
        book2.setId(2L);
        book2.setIsbn(97831);
        book2.setTitle("Kitab 2");
        book2.setAuthor(new Author());
        book2.setCategory(new Category());
        book2.setStock(5);
        book2.setImage("book2.jpg");
        book2.setDescription("A description of Kitab 2");

        Mockito.when(bookRepository.findAll()).thenReturn(Arrays.asList(book, book2));

        // Method call
        var books = bookService.getAllBooks();

        // Assertions
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("Kitab 1", books.get(0).getTitle());
        assertEquals("Kitab 2", books.get(1).getTitle());
    }

    @Test
    void testCreateBook() {
        // save metodunun qaytardığı nəticəni mock edirik
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Method call
        bookService.createBook(book);

        // Verifying save was called
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }


    @Test
    void testGetBookById() {
        // Mocking findById to return the book object
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Method call
        Book fetchedBook = bookService.getBookById(1L);

        // Assertions
        assertNotNull(fetchedBook);
        assertEquals("Kitab 1", fetchedBook.getTitle());
        assertEquals(1L, fetchedBook.getId());
    }

    @Test
    void testGetBookById_ThrowsException_WhenNotFound() {
        // Mocking findById to return empty (not found)
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Method call & Exception assertion
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookService.getBookById(1L);
        });

        // Verifying exception message
        assertEquals("Kitab tapılmadı: 1", exception.getMessage());
    }

    @Test
    void testUpdateBook() {
        // Mocking findById to return the existing book
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Prepare updated book object
        Book updatedBook = new Book();
        updatedBook.setIsbn(97831);
        updatedBook.setTitle("Kitab 1 Updated");
        updatedBook.setAuthor(author);
        updatedBook.setCategory(category);
        updatedBook.setStock(15);
        updatedBook.setImage("book1_updated.jpg");
        updatedBook.setDescription("Updated description");

        // Method call
        bookService.updateBook(1L, updatedBook);

        // Verifying that repository save method is called with updated book
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));

        // Fetch updated book and validate changes
        Book fetchedUpdatedBook = bookService.getBookById(1L);
        assertEquals("Kitab 1 Updated", fetchedUpdatedBook.getTitle());
        assertEquals(15, fetchedUpdatedBook.getStock());
    }

    @Test
    void testDeleteBook() {
        // Mocking findById to return the existing book
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Mocking delete method
        Mockito.doNothing().when(bookRepository).delete(book);

        // Method call
        bookService.deleteBook(1L);

        // Verifying that delete method was called once
        Mockito.verify(bookRepository, Mockito.times(1)).delete(book);
    }
}
