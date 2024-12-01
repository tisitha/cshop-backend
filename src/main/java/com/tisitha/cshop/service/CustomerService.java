package com.tisitha.cshop.service;

import com.tisitha.cshop.model.Customer;
import com.tisitha.cshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    Map<String,String> res = new HashMap<>();

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<String> register(Customer customer) {
        if(customerRepository.findByEmail(customer.getEmail())==null){
            customer.setPassword(encoder.encode(customer.getPassword()));
            customerRepository.save(customer);
            return new ResponseEntity<>("Your account is successfully registered",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Email has taken",HttpStatus.CONFLICT);
    }

    public ResponseEntity<?> login(Customer customer){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getEmail(),customer.getPassword()));
        if(authentication.isAuthenticated()){
            String cid = (customerRepository.findByEmail(customer.getEmail())).getId().toString();
            String firstname = (customerRepository.findByEmail(customer.getEmail())).getFirstname();
            res.put("cid",cid);
            res.put("firstname",firstname);
            res.put("token",jwtService.generateToken(customer.getEmail()));
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        return new ResponseEntity<>("Incorrect username or password",HttpStatus.UNAUTHORIZED);
    }

}