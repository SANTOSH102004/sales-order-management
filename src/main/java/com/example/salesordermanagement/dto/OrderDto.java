package com.example.salesordermanagement.dto;

import com.example.salesordermanagement.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String orderNumber;
    private CustomerDto customer;
    private List<OrderItemDto> items;
    private OrderStatus status;
    private Double subtotal;
    private Double tax;
    private Double shipping;
    private Double total;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;
    private String notes;
    private UserDto createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
