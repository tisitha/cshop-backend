package com.tisitha.cshop.controller;

import com.tisitha.cshop.model.Customer;
import com.tisitha.cshop.service.CustomerService;
import com.tisitha.cshop.service.ForgotPasswordService;
import com.tisitha.cshop.util.ChangePassword;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class CustomerController {

    private final CustomerService customerService;

    private final ForgotPasswordService forgotPasswordService;

    public CustomerController(CustomerService customerService, ForgotPasswordService forgotPasswordService) {
        this.customerService = customerService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer customer){
        return customerService.login(customer);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer){
        return customerService.register(customer);
    }

    @PostMapping("/verifymail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email){
        return forgotPasswordService.verifyEmail(email);

    }
    @PostMapping("/varifyotp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp,@PathVariable String email){
        return forgotPasswordService.verifyOtp(otp,email);
    }

    @PostMapping("/changepassword/{otp}/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword,@PathVariable Integer otp,@PathVariable String email){
        return forgotPasswordService.changePasswordHandler(changePassword,otp,email);
    }

}