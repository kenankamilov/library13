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

    @GetMapping
    public String listAuthors(Model model) {
        logger.info("Bütün müəlliflər gətirilir");
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "authors/list";
    }

    @GetMapping("/create")
    public String createAuthorForm(Model model) {
        logger.info("Müəllif yaratma forması göstərilir");
        model.addAttribute("author", new Author());
        return "authors/create";
    }

    @PostMapping("/create")
    public String createAuthor(@ModelAttribute Author author) {
        logger.info("Yeni müəllif yaradılır: {}", author.getName());
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/update/{id}")
    public String updateAuthorForm(@PathVariable Long id, Model model) {
        logger.info("ID-si {} olan müəllif üçün yeniləmə forması göstərilir", id);
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "authors/update";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute Author author) {
        logger.info("ID-si {} olan müəllif yenilənir", id);
        authorService.updateAuthor(id, author);
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        logger.info("ID-si {} olan müəllif silinir", id);
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }

    @GetMapping("/{id}")
    public String viewAuthor(@PathVariable Long id, Model model) {
        logger.info("ID-si {} olan müəllifin detalları göstərilir", id);
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "authors/view";
    }
}
