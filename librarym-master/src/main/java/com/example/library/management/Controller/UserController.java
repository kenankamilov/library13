package com.example.library.management.Controller;

import com.example.library.management.Model.User;
import com.example.library.management.Service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final CustomUserDetailsService userService;

    public UserController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        logger.info("Admin daxilolma səhifəsi göstərilir");
        return "admin/login";  // Login səhifəsinə yönləndirir
    }

    @GetMapping("/home")
    public String websiteHome() {
        logger.info("Admin paneli göstərilir");
        return "admin/home";  // Admin paneli
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        logger.info("Yeni admin qeydiyyatı forması göstərilir");
        model.addAttribute("user", new User());
        return "admin/register";  // Yeni admin qeydiyyatı üçün
    }

    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute User user) {
        logger.info("Yeni admin qeydiyyatı: {}", user.getUsername());
        userService.registerAdmin(user);
        return "redirect:/admin/login?registered";
    }
}
