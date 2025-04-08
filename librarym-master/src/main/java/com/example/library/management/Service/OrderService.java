package com.example.library.management.Service;

import com.example.library.management.Model.Order;
import com.example.library.management.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;  // Add this import
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null); // Return null if not found
    }

    // Create a new order
    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    // Return a book (update return timestamp)
    public void returnOrder(Long id) {
        Order order = getOrderById(id);
        if (order != null) {
            order.setReturnTimestamp(LocalDateTime.now());  // Use LocalDateTime here
            orderRepository.save(order);
        }
    }

    // Delete an order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
