package com.tisitha.cshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imgUrl;
    private double price;
    private String category;
    private boolean isNew;
    private boolean isTop;
    private double deal;
    private String description;
}