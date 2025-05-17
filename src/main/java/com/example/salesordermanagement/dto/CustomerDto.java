package com.example.salesordermanagement.dto;

import com.example.salesordermanagement.model.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String company;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String notes;
    private CustomerStatus status;
    private double totalSpent;
    private int totalOrders;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
