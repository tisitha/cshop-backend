package com.tisitha.cshop.service;

import com.tisitha.cshop.dto.Mailbody;
import com.tisitha.cshop.model.Customer;
import com.tisitha.cshop.model.ForgotPassword;
import com.tisitha.cshop.repository.CustomerRepository;
import com.tisitha.cshop.repository.ForgotPasswordRepository;
import com.tisitha.cshop.util.ChangePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
public class ForgotPasswordService {

    private final CustomerRepository customerRepository;

    private final EmailService emailService;

    private final ForgotPasswordRepository forgotPasswordRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ForgotPasswordService(CustomerRepository customerRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository) {
        this.customerRepository = customerRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    public ResponseEntity<String> verifyEmail(String email){
        Customer customer = customerRepository.findByEmail(email);
        if(customer==null){
          return new ResponseEntity<>("Email not found", HttpStatus.NOT_FOUND);
        }
        forgotPasswordRepository.deleteByCustomerId(customer.getId());
        int otp = otpGenerator();
        Mailbody mailbody = Mailbody.builder()
                .to(email)
                .text("This is the OPT for your forgot password request "+otp)
                .subject("OTP for forgot password request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis()+60*1000*5))
                .customer(customer)
                .build();

        emailService.sendSimpleMessage(mailbody);

        forgotPasswordRepository.save(fp);

        return ResponseEntity.ok("Email sent for verification");

    }

    public ResponseEntity<String> verifyOtp(Integer otp,String email){
        Customer customer = customerRepository.findByEmail(email);

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndCustomer(otp,customer).orElseThrow(()->new RuntimeException("Invalid OTP and email"+email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("Otp expired", HttpStatus.REQUEST_TIMEOUT);
        }

        return ResponseEntity.ok("Otp verified");
    }

    public ResponseEntity<String> changePasswordHandler(ChangePassword changePassword,Integer otp,String email){
        Customer customer = customerRepository.findByEmail(email);

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndCustomer(otp,customer).orElseThrow(()->new RuntimeException("Invalid OTP and email"+email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("Otp expired", HttpStatus.REQUEST_TIMEOUT);
        }

        if(!Objects.equals(changePassword.password(),changePassword.repeatPassword())){
            return new ResponseEntity<>("Please enter the password again",HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        customerRepository.updatePassword(email,encodedPassword);

        forgotPasswordRepository.deleteById(fp.getFpid());

        return ResponseEntity.ok("Password has been changed");
    }

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100000,999999);
    }
}