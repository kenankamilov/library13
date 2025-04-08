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

    // ✅ List all orders
    @GetMapping
    public String listOrders(Model model) {
        logger.info("Fetching all orders");
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    // ✅ Show order creation form
    @GetMapping("/create")
    public String createOrderForm(Model model) {
        logger.info("Displaying order creation form");
        model.addAttribute("order", new Order());
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("students", studentService.getAllStudents());
        return "orders/create";
    }

    // ✅ Handle order creation form submission
    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order) {
        logger.info("Creating a new order");
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    // ✅ Handle book return
    @PostMapping("/return/{id}")
    public String returnOrder(@PathVariable Long id) {
        logger.info("Returning book for order ID: {}", id);
        orderService.returnOrder(id);
        return "redirect:/orders";
    }

    // ✅ Delete an order
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        logger.info("Deleting order ID: {}", id);
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

    // ✅ View order details
    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        logger.info("Viewing details for order ID: {}", id);
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "orders/view";
    }
}