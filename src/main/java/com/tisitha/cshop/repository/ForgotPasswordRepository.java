package com.tisitha.cshop.repository;

import com.tisitha.cshop.model.Customer;
import com.tisitha.cshop.model.ForgotPassword;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Long> {

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.customer = ?2")
    Optional<ForgotPassword> findByOtpAndCustomer(Integer otp, Customer customer);

    @Modifying
    @Transactional
    @Query("DELETE FROM ForgotPassword fb WHERE fb.customer.id = ?1")
    void deleteByCustomerId(Long id);
}
