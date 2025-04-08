package com.example.library.management.Controller;

import com.example.library.management.Model.User;
import com.example.library.management.Service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class UserController {

    private final CustomUserDetailsService userService;

    public UserController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
   public String loginForm() {
       return "admin/login";  // Login səhifəsinə yönləndirir
   }



    @GetMapping("/home")
    public String websiteHome() {
        return "admin/home";  // Admin paneli
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/register";  // Yeni admin qeydiyyatı üçün
    }

    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute User user) {
        userService.registerAdmin(user);
        return "redirect:/admin/login?registered";
    }
}
