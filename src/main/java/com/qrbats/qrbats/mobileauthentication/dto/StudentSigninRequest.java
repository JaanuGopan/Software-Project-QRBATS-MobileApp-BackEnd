package com.qrbats.qrbats.mobileauthentication.dto;

import lombok.Data;

@Data
public class StudentSigninRequest {

    private String studentUserName;
    private String password;
}
