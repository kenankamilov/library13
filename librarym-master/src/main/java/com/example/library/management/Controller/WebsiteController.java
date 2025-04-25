package com.example.library.management.Controller;

import com.example.library.management.Model.Book;
import com.example.library.management.Service.BookService;
import com.example.library.management.Service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/website")
public class WebsiteController {

    private static final Logger logger = LoggerFactory.getLogger(WebsiteController.class);

    private final BookService bookService;
    private final AuthorService authorService;

    public WebsiteController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public String books(Model model) {
        logger.info("Kitablar səhifəsi göstərilir");
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "website/books";
    }

    @GetMapping("/authors")
    public String authors(Model model) {
        logger.info("Müəlliflər səhifəsi göstərilir");
        model.addAttribute("authors", authorService.getAllAuthors());
        return "website/authors";
    }

    @GetMapping("/contact")
    public String contact() {
        logger.info("Əlaqə səhifəsi göstərilir");
        return "website/contact";
    }
}
