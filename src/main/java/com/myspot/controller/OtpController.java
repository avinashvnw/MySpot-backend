package com.myspot.controller;

import com.myspot.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {
    private final CustomerService customerService;

    public OtpController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(
            @RequestParam String phoneNumber, @RequestParam String otp) {
        if (customerService.verifyOtp(phoneNumber, otp)) {
            return ResponseEntity.ok("OTP verified successfully");
        }
        return ResponseEntity.badRequest().body("Invalid OTP");
    }
}