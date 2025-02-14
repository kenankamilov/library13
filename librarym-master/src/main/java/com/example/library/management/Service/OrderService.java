package com.example.library.management.Service;

import com.example.library.management.Model.Book;
import com.example.library.management.Model.Order;
import com.example.library.management.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookService bookService;

    // Constructor Injection
    public OrderService(OrderRepository orderRepository, BookService bookService) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
    }

    public Order createOrder(Order order) {
        if (order.getBook() == null) {
            throw new RuntimeException("Book cannot be null");
        }

        Book book = order.getBook();
        if (book.getStock() == null || book.getStock() <= 0) {
            throw new RuntimeException("Book is out of stock");
        }

        book.decreaseStock();
        bookService.saveBook(book); // Save the updated book
        order.setOrderTimestamp(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order returnOrder(Order order) {
        if (order == null || order.getBook() == null) {
            throw new RuntimeException("Invalid order or book not found");
        }

        Book book = order.getBook();
        book.increaseStock();
        bookService.saveBook(book); // Save the updated book
        order.setReturned(true);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByStudent(Long studentId) {
        return orderRepository.findByStudentId(studentId);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
