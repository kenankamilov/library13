package com.example.library.management.Controller;


import com.example.library.management.Model.Author;
import com.example.library.management.Model.Book;
import com.example.library.management.Model.Category;
import com.example.library.management.Service.AuthorService;
import com.example.library.management.Service.BookService;
import com.example.library.management.Service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OpenAPIController {

    @Autowired
    private com.example.library.management.Service.BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired

    private AuthorService authorService;

    // Get books by category with pagination
    @GetMapping("/books/category/{categoryId}")
    public ResponseEntity<Page<Book>> getBooksByCategory(@PathVariable Long categoryId, Pageable pageable) {
        try {
            Category category = new Category();
            category.setId(categoryId);

            Page<Book> booksPage = bookService.getBooksByCategory(category, pageable);
            if (booksPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(booksPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            if (categories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAuthors() {
        try {
            List<Author> authors = authorService.getAllAuthors();
            if (authors.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(authors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            Book book = bookService.getBookDetails(id);
            if (book == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
