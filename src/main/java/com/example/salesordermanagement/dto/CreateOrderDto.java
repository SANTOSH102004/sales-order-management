package com.example.salesordermanagement.dto;

import com.example.salesordermanagement.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {
    private Long customerId;
    private List<OrderItemRequest> items;
    private OrderStatus status;
    private Double shipping;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;
    private String notes;
}
