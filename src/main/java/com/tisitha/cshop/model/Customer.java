package com.tisitha.cshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String address1;
    private String address2;
    private String phoneNo;

    @OneToOne(mappedBy = "customer")
    private ForgotPassword forgotPassword;

}