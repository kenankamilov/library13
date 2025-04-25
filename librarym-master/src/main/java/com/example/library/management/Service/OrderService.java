package com.example.library.management.Service;

import com.example.library.management.Model.Order;
import com.example.library.management.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new IllegalArgumentException("Sifariş tapılmadı: " + id));
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public void returnOrder(Long id) {
        Order order = getOrderById(id);
        if (order != null) {
            order.setReturnTimestamp(LocalDateTime.now());
            orderRepository.save(order);
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
