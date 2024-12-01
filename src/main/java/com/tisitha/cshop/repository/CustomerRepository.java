package com.tisitha.cshop.repository;

import com.tisitha.cshop.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Customer c set c.password = ?2 where c.email = ?1")
    void updatePassword(String email,String password);
}