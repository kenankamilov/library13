package com.example.library.management.Controller;

import com.example.library.management.Model.Book;
import com.example.library.management.Service.BookService;
import com.example.library.management.Service.AuthorService;
import com.example.library.management.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listBooks(Model model) {
        logger.info("Bütün kitablar gətirilir");
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/create")
    public String createBookForm(Model model) {
        logger.info("Kitab əlavəetmə forması göstərilir");
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "books/create";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute Book book, @RequestParam("imageFile") MultipartFile imageFile) {
        logger.info("Yeni kitab yaradılır: {}", book.getTitle());

        if (!imageFile.isEmpty()) {
            // Unikal şəkil adı yaratmaq üçün zaman damğası ilə birləşdiririk
            String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            String uploadDir = "src/main/resources/static/"; // Şəkilləri static qovluğunda saxlayırıq

            try {
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    boolean dirsCreated = uploadPath.mkdirs(); // Qovluğu yarat və nəticəni saxla
                    if (!dirsCreated) {
                        logger.warn("Qovluq yaradılmadı: {}", uploadDir); // Əgər qovluq yaradılmadısa xəbərdarlıq et
                    }
                }

                // Şəkili həmin qovluğa yükləyirik
                imageFile.transferTo(new File(uploadDir + imageName));

                // Şəkil URL-i
                book.setImage("/" + imageName); // Şəkil yolunu düz təqdim et
            } catch (IOException e) {
                logger.error("Şəkil yüklənərkən xəta baş verdi", e);
            }
        }

        // Kitabın məlumatlarını bazaya əlavə edirik
        bookService.createBook(book);
        return "redirect:/books"; // Kitablar siyahısına yönləndiririk
    }

    @GetMapping("/update/{id}")
    public String updateBookForm(@PathVariable Long id, Model model) {
        logger.info("ID-si {} olan kitab üçün yeniləmə forması göstərilir", id);
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "books/update";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book) {
        logger.info("ID-si {} olan kitab yenilənir", id);
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        logger.info("ID-si {} olan kitab silinir", id);
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        logger.info("ID-si {} olan kitabın detalları göstərilir", id);
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "books/view";
    }
}
