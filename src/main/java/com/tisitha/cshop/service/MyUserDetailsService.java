package com.tisitha.cshop.service;

import com.tisitha.cshop.model.Customer;
import com.tisitha.cshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer =  customerRepository.findByEmail(email);
        if (customer==null){
            throw new UsernameNotFoundException("Email not found");
        }
        return new MyUserDetails(customer);
    }
}