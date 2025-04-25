package com.example.library.management.Controller;

import com.example.library.management.Model.Order;
import com.example.library.management.Service.OrderService;
import com.example.library.management.Service.BookService;
import com.example.library.management.Service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final BookService bookService;
    private final StudentService studentService;

    public OrderController(OrderService orderService, BookService bookService, StudentService studentService) {
        this.orderService = orderService;
        this.bookService = bookService;
        this.studentService = studentService;
    }

    @GetMapping
    public String listOrders(Model model) {
        logger.info("Bütün sifarişlər gətirilir");
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @GetMapping("/create")
    public String createOrderForm(Model model) {
        logger.info("Sifariş əlavəetmə forması göstərilir");
        model.addAttribute("order", new Order());
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("students", studentService.getAllStudents());
        return "orders/create";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order) {
        logger.info("Yeni sifariş yaradılır");
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    @PostMapping("/return/{id}")
    public String returnOrder(@PathVariable Long id) {
        logger.info("ID-si {} olan sifariş üçün kitab qaytarılır", id);
        orderService.returnOrder(id);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        logger.info("ID-si {} olan sifariş silinir", id);
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        logger.info("ID-si {} olan sifarişin detalları göstərilir", id);
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "orders/view";
    }
}
