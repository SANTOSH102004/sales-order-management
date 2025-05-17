package com.example.salesordermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse {
    private Double totalSales;
    private Long totalOrders;
    private Double averageOrderValue;
    private List<Map<String, Object>> salesByDate;
    private List<Map<String, Object>> ordersByStatus;
    private List<Map<String, Object>> topProducts;
    private List<Map<String, Object>> topCustomers;
}
