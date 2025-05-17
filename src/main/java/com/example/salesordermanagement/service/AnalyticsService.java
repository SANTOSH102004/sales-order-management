package com.example.salesordermanagement.service;

import com.example.salesordermanagement.dto.AnalyticsResponse;
import com.example.salesordermanagement.model.Order;
import com.example.salesordermanagement.model.OrderItem;
import com.example.salesordermanagement.model.Product;
import com.example.salesordermanagement.repository.CustomerRepository;
import com.example.salesordermanagement.repository.OrderRepository;
import com.example.salesordermanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public AnalyticsResponse getAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        // Get orders in date range
        List<Order> orders = orderRepository.findByDateRange(startDate, endDate);
        
        // Calculate total sales amount
        Double totalSales = orders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
        
        // Calculate average order value
        Double averageOrderValue = orders.isEmpty() ? 0 : totalSales / orders.size();
        
        // Get sales by date
        Map<LocalDate, Double> salesByDateMap = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().toLocalDate(),
                        Collectors.summingDouble(Order::getTotal)
                ));
        
        List<Map<String, Object>> salesByDate = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        salesByDateMap.forEach((date, amount) -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("date", date.format(formatter));
            entry.put("amount", amount);
            salesByDate.add(entry);
        });
        
        // Sort by date
        salesByDate.sort(Comparator.comparing(entry -> LocalDate.parse((String) entry.get("date"))));
        
        // Get orders by status
        List<Object[]> ordersByStatusData = orderRepository.getOrderCountsByStatus();
        List<Map<String, Object>> ordersByStatus = new ArrayList<>();
        
        for (Object[] row : ordersByStatusData) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("status", row[0].toString());
            entry.put("count", row[1]);
            ordersByStatus.add(entry);
        }
        
        // Get top products
        Map<Product, Integer> productSales = new HashMap<>();
        
        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                productSales.put(product, productSales.getOrDefault(product, 0) + item.getQuantity());
            }
        }
        
        List<Map<String, Object>> topProducts = productSales.entrySet().stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> {
                    Map<String, Object> productData = new HashMap<>();
                    Product product = entry.getKey();
                    productData.put("id", product.getId());
                    productData.put("name", product.getName());
                    productData.put("sku", product.getSku());
                    productData.put("quantity", entry.getValue());
                    productData.put("revenue", entry.getValue() * product.getPrice());
                    return productData;
                })
                .collect(Collectors.toList());
        
        // Get top customers from repository
        Pageable top5 = PageRequest.of(0, 5);
        List<Map<String, Object>> topCustomers = customerRepository.findTopCustomersBySpent(top5)
                .stream()
                .map(customer -> {
                    Map<String, Object> customerData = new HashMap<>();
                    customerData.put("id", customer.getId());
                    customerData.put("name", customer.getName());
                    customerData.put("totalSpent", customer.getTotalSpent());
                    customerData.put("totalOrders", customer.getTotalOrders());
                    return customerData;
                })
                .collect(Collectors.toList());
        
        return AnalyticsResponse.builder()
                .totalSales(totalSales)
                .totalOrders((long) orders.size())
                .averageOrderValue(averageOrderValue)
                .salesByDate(salesByDate)
                .ordersByStatus(ordersByStatus)
                .topProducts(topProducts)
                .topCustomers(topCustomers)
                .build();
    }
    
    public Map<String, Double> getSalesMetrics() {
        Map<String, Double> metrics = new HashMap<>();
        
        // Today's sales
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        Double todaySales = orderRepository.getTotalSalesForDateRange(startOfDay, endOfDay);
        metrics.put("todaySales", todaySales != null ? todaySales : 0.0);
        
        // This week's sales
        LocalDate startOfWeek = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
        LocalDateTime startOfWeekDateTime = LocalDateTime.of(startOfWeek, LocalTime.MIN);
        Double weekSales = orderRepository.getTotalSalesForDateRange(startOfWeekDateTime, endOfDay);
        metrics.put("weekSales", weekSales != null ? weekSales : 0.0);
        
        // This month's sales
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDateTime startOfMonthDateTime = LocalDateTime.of(startOfMonth, LocalTime.MIN);
        Double monthSales = orderRepository.getTotalSalesForDateRange(startOfMonthDateTime, endOfDay);
        metrics.put("monthSales", monthSales != null ? monthSales : 0.0);
        
        // Year to date sales
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        LocalDateTime startOfYearDateTime = LocalDateTime.of(startOfYear, LocalTime.MIN);
        Double yearSales = orderRepository.getTotalSalesForDateRange(startOfYearDateTime, endOfDay);
        metrics.put("yearSales", yearSales != null ? yearSales : 0.0);
        
        return metrics;
    }
}
