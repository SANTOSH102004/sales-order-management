package com.example.salesordermanagement.service;

import com.example.salesordermanagement.dto.*;
import com.example.salesordermanagement.model.*;
import com.example.salesordermanagement.repository.CustomerRepository;
import com.example.salesordermanagement.repository.OrderRepository;
import com.example.salesordermanagement.repository.ProductRepository;
import com.example.salesordermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Page<OrderDto> getAllOrders(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        return orderRepository.findAll(pageable)
                .map(this::mapToDto);
    }

    public Page<OrderDto> getOrdersByCustomer(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findByCustomerId(customerId, pageable)
                .map(this::mapToDto);
    }

    public Page<OrderDto> getOrdersByStatus(OrderStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findByStatus(status, pageable)
                .map(this::mapToDto);
    }

    public Page<OrderDto> searchOrders(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findByOrderNumberContainingIgnoreCase(query, pageable)
                .map(this::mapToDto);
    }

    public OrderDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    public OrderDto createOrder(CreateOrderDto orderDto) {
        Customer customer = customerRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Order order = Order.builder()
                .customer(customer)
                .status(orderDto.getStatus() != null ? orderDto.getStatus() : OrderStatus.PENDING)
                .shipping(orderDto.getShipping())
                .shippingAddress(orderDto.getShippingAddress())
                .billingAddress(orderDto.getBillingAddress())
                .paymentMethod(orderDto.getPaymentMethod())
                .notes(orderDto.getNotes())
                .createdBy(currentUser)
                .items(new ArrayList<>())
                .build();
        
        orderRepository.save(order);
        
        for (OrderItemRequest itemRequest : orderDto.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.getProductId()));
            
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrice())
                    .build();
            
            order.addItem(orderItem);
            
            // Update product stock
            if (product.getStockQuantity() != null) {
                product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
                productRepository.save(product);
            }
        }
        
        order.calculateTotals();
        
        // Update customer metrics
        customer.setTotalOrders(customer.getTotalOrders() + 1);
        customer.setTotalSpent(customer.getTotalSpent() + order.getTotal());
        customerRepository.save(customer);
        
        Order savedOrder = orderRepository.save(order);
        return mapToDto(savedOrder);
    }

    @Transactional
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(orderDto.getStatus());
        order.setShipping(orderDto.getShipping());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setBillingAddress(orderDto.getBillingAddress());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setNotes(orderDto.getNotes());
        order.setUpdatedAt(LocalDateTime.now());
        
        order.calculateTotals();
        
        Order savedOrder = orderRepository.save(order);
        return mapToDto(savedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        Customer customer = order.getCustomer();
        customer.setTotalOrders(customer.getTotalOrders() - 1);
        customer.setTotalSpent(customer.getTotalSpent() - order.getTotal());
        customerRepository.save(customer);
        
        // Restore product stock
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            if (product.getStockQuantity() != null) {
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                productRepository.save(product);
            }
        }
        
        orderRepository.deleteById(id);
    }
    
    private OrderDto mapToDto(Order order) {
        List<OrderItemDto> itemDtos = order.getItems().stream()
                .map(this::mapToItemDto)
                .collect(Collectors.toList());
        
        CustomerDto customerDto = CustomerDto.builder()
                .id(order.getCustomer().getId())
                .name(order.getCustomer().getName())
                .email(order.getCustomer().getEmail())
                .build();
        
        UserDto createdByDto = null;
        if (order.getCreatedBy() != null) {
            createdByDto = UserDto.builder()
                    .id(order.getCreatedBy().getId())
                    .firstName(order.getCreatedBy().getFirstName())
                    .lastName(order.getCreatedBy().getLastName())
                    .email(order.getCreatedBy().getEmail())
                    .build();
        }
        
        return OrderDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customer(customerDto)
                .items(itemDtos)
                .status(order.getStatus())
                .subtotal(order.getSubtotal())
                .tax(order.getTax())
                .shipping(order.getShipping())
                .total(order.getTotal())
                .shippingAddress(order.getShippingAddress())
                .billingAddress(order.getBillingAddress())
                .paymentMethod(order.getPaymentMethod())
                .notes(order.getNotes())
                .createdBy(createdByDto)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
    
    private OrderItemDto mapToItemDto(OrderItem item) {
        ProductDto productDto = ProductDto.builder()
                .id(item.getProduct().getId())
                .name(item.getProduct().getName())
                .sku(item.getProduct().getSku())
                .price(item.getProduct().getPrice())
                .imageUrl(item.getProduct().getImageUrl())
                .build();
        
        return OrderItemDto.builder()
                .id(item.getId())
                .product(productDto)
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .total(item.getTotal())
                .build();
    }
}
