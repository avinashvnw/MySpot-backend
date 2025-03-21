package com.myspot.controller;

import com.myspot.entity.Customer;
import com.myspot.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }
}