package com.qrbats.qrbats.mobileauthentication.entities.otp.otpverification.service;

import com.qrbats.qrbats.mobileauthentication.entities.otp.repository.OTPRepository;
import org.springframework.stereotype.Service;

public interface OTPVerificationService {

     boolean sendOTP(String email);
     void sendEmail(String toEmail,String subject, String boady);
     String generateOTP();
     void deleteExpiredOTPs();

     boolean otpVerification(String studentEmail,String otp);

}
