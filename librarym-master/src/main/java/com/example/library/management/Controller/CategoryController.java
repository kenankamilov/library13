package com.example.library.management.Controller;

import com.example.library.management.Model.Category;
import com.example.library.management.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        logger.info("Bütün kateqoriyalar gətirilir");
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/list";
    }

    @GetMapping("/create")
    public String createCategoryForm() {
        logger.info("Kateqoriya əlavəetmə forması göstərilir");
        return "categories/create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute Category category) {
        logger.info("Yeni kateqoriya yaradılır: {}", category.getName());
        categoryService.createCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/update/{id}")
    public String updateCategoryForm(@PathVariable Long id, Model model) {
        logger.info("ID-si {} olan kateqoriya üçün yeniləmə forması göstərilir", id);
        model.addAttribute("category", categoryService.getCategoryById(id).orElse(null));
        return "categories/update";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        logger.info("ID-si {} olan kateqoriya yenilənir", id);
        categoryService.updateCategory(id, category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        logger.info("ID-si {} olan kateqoriya silinir", id);
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }

    @GetMapping("/{id}")
    public String viewCategory(@PathVariable Long id, Model model) {
        logger.info("ID-si {} olan kateqoriyanın detalları göstərilir", id);
        model.addAttribute("category", categoryService.getCategoryById(id).orElse(null));
        return "categories/view";
    }
}
