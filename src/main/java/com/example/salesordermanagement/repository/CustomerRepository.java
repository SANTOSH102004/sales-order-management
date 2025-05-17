package com.example.salesordermanagement.repository;

import com.example.salesordermanagement.model.Customer;
import com.example.salesordermanagement.model.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    Page<Customer> findByStatus(CustomerStatus status, Pageable pageable);
    
    @Query("SELECT c FROM Customer c ORDER BY c.totalOrders DESC")
    List<Customer> findTopCustomersByOrders(Pageable pageable);
    
    @Query("SELECT c FROM Customer c ORDER BY c.totalSpent DESC")
    List<Customer> findTopCustomersBySpent(Pageable pageable);
    
    boolean existsByEmail(String email);
}
