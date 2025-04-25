package com.example.library.management;

import com.example.library.management.Model.Book;
import com.example.library.management.Model.Order;
import com.example.library.management.Model.Student;
import com.example.library.management.Repository.OrderRepository;
import com.example.library.management.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Book book;

    @Mock
    private Student student;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Creating a mock order object
        order = new Order();
        order.setId(1L);
        order.setBook(book);
        order.setStudent(student);
        order.setOrderTimestamp(LocalDateTime.now());
    }

    @Test
    public void testCreateOrder() {
        // Create an order
        orderService.createOrder(order);

        // Verify that save method was called on repository
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetOrderById() {
        // When finding an order by ID, return the mock order
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Retrieve the order by ID
        Order foundOrder = orderService.getOrderById(1L);
        assertNotNull(foundOrder);
        assertEquals(order.getId(), foundOrder.getId());
    }

    @Test
    public void testGetOrderByIdNotFound() {
        // Simulate that the order with ID doesn't exist
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Try to retrieve the order and check that it's null
        Order foundOrder = orderService.getOrderById(1L);
        assertNull(foundOrder);
    }

    @Test
    public void testReturnOrder() {
        // When the order is found, update the return timestamp
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Perform the return operation
        orderService.returnOrder(1L);

        // Ensure the return timestamp is updated
        assertNotNull(order.getReturnTimestamp());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testReturnOrderNotFound() {
        // Simulate that the order doesn't exist
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Return order without throwing any exception
        orderService.returnOrder(1L);

        // Verify that save was not called since the order was not found
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    public void testDeleteOrder() {
        // Simulate that the order exists and can be deleted
        doNothing().when(orderRepository).deleteById(1L);

        // Perform deletion
        orderService.deleteOrder(1L);

        // Verify that the delete method was called on repository
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteOrderNotFound() {
        // Simulate the scenario where the order doesn't exist
        doNothing().when(orderRepository).deleteById(1L);

        // Delete order without exceptions
        orderService.deleteOrder(1L);

        // Verify delete was called on repository
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllOrders() {
        // Simulate retrieval of all orders
        when(orderRepository.findAll()).thenReturn(List.of(order));

        // Retrieve all orders
        assertEquals(1, orderService.getAllOrders().size());
        verify(orderRepository, times(1)).findAll();
    }
}
