package com.myspot.service;

import com.myspot.entity.Customer;
import com.myspot.repository.CustomerRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> otpStore = new HashMap<>();

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer registerCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer savedCustomer = customerRepository.save(customer);
        sendOtp(savedCustomer.getPhoneNumber());
        return savedCustomer;
    }

    private void sendOtp(String phoneNumber) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStore.put(phoneNumber, otp);
        String message = "Your MySpot OTP is: " + otp;
        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                message
        ).create();
    }

    public boolean verifyOtp(String phoneNumber, String otp) {
        String storedOtp = otpStore.get(phoneNumber);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStore.remove(phoneNumber); // Clear after verification
            return true;
        }
        return false;
    }
}