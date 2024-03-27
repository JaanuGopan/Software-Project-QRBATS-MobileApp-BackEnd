package com.qrbats.qrbats.mobileauthentication.dto;

import com.qrbats.qrbats.mobileauthentication.services.impl.StudentServiceImpl;
import lombok.Data;

@Data
public class StudentOTPVerificationRequest {
    private String studentEmail;
    private String otp;
}
