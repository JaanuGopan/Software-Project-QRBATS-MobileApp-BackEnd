package com.qrbats.qrbats.mobileauthentication.dto;

import lombok.Data;

@Data
public class StudentJwtAuthenticationResponse {
    private String token;
    private String refreshToken;
}
