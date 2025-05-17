package com.example.salesordermanagement.service;

import com.example.salesordermanagement.dto.CustomerDto;
import com.example.salesordermanagement.model.Customer;
import com.example.salesordermanagement.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Page<CustomerDto> getAllCustomers(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerRepository.findAll(pageable)
                .map(this::mapToDto);
    }

    public Page<CustomerDto> searchCustomers(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findByNameContainingIgnoreCase(query, pageable)
                .map(this::mapToDto);
    }

    public CustomerDto getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        Customer customer = mapToEntity(customerDto);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        return mapToDto(savedCustomer);
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        // Check if email is being changed and if it already exists
        if (!customer.getEmail().equals(customerDto.getEmail()) && 
                customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());
        customer.setCompany(customerDto.getCompany());
        customer.setAddress(customerDto.getAddress());
        customer.setCity(customerDto.getCity());
        customer.setState(customerDto.getState());
        customer.setZipCode(customerDto.getZipCode());
        customer.setCountry(customerDto.getCountry());
        customer.setNotes(customerDto.getNotes());
        customer.setStatus(customerDto.getStatus());
        customer.setUpdatedAt(LocalDateTime.now());
        
        Customer savedCustomer = customerRepository.save(customer);
        return mapToDto(savedCustomer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
    
    public List<CustomerDto> getTopCustomers(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return customerRepository.findTopCustomersBySpent(pageable).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    private CustomerDto mapToDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .company(customer.getCompany())
                .address(customer.getAddress())
                .city(customer.getCity())
                .state(customer.getState())
                .zipCode(customer.getZipCode())
                .country(customer.getCountry())
                .notes(customer.getNotes())
                .status(customer.getStatus())
                .totalSpent(customer.getTotalSpent())
                .totalOrders(customer.getTotalOrders())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
    
    private Customer mapToEntity(CustomerDto customerDto) {
        return Customer.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhone())
                .company(customerDto.getCompany())
                .address(customerDto.getAddress())
                .city(customerDto.getCity())
                .state(customerDto.getState())
                .zipCode(customerDto.getZipCode())
                .country(customerDto.getCountry())
                .notes(customerDto.getNotes())
                .status(customerDto.getStatus())
                .totalSpent(customerDto.getTotalSpent())
                .totalOrders(customerDto.getTotalOrders())
                .build();
    }
}
