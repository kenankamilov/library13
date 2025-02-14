package com.example.library.management.Controller;


import com.example.library.management.Model.Order;
import com.example.library.management.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/return/{id}")
    public Order returnOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        return orderService.returnOrder(order);
    }

    @GetMapping("/student/{studentId}")
    public List<Order> getOrdersByStudent(@PathVariable Long studentId) {
        return orderService.getOrdersByStudent(studentId);
    }
}
