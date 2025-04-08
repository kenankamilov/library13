package com.example.library.management.Controller;

import com.example.library.management.Model.Author;
import com.example.library.management.Service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // ✅ List all authors
    @GetMapping
    public String listAuthors(Model model) {
        logger.info("Fetching all authors");
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "authors/list";  // Thymeleaf template: authors/list.html
    }

    // ✅ Show author creation form
    @GetMapping("/create")
    public String createAuthorForm(Model model) {
        logger.info("Displaying author creation form");
        model.addAttribute("author", new Author());
        return "authors/create";  // Thymeleaf template: authors/create.html
    }

    // ✅ Handle author creation form submission
    @PostMapping("/create")
    public String createAuthor(@ModelAttribute Author author) {
        logger.info("Creating a new author: {}", author.getName());
        authorService.saveAuthor(author); // use saveAuthor instead of createAuthor
        return "redirect:/authors";
    }

    // ✅ Show author update form
    @GetMapping("/update/{id}")
    public String updateAuthorForm(@PathVariable Long id, Model model) {
        logger.info("Displaying update form for author ID: {}", id);
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "authors/update";  // Thymeleaf template: authors/update.html
    }

    // ✅ Handle author update form submission
    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute Author author) {
        logger.info("Updating author ID: {}", id);
        authorService.updateAuthor(id, author);  // Correcting this method
        return "redirect:/authors";
    }

    // ✅ Delete an author
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        logger.info("Deleting author ID: {}", id);
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }

    // ✅ View details of a single author
    @GetMapping("/{id}")
    public String viewAuthor(@PathVariable Long id, Model model) {
        logger.info("Viewing details for author ID: {}", id);
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "authors/view";  // Thymeleaf template: authors/view.html
    }
}
