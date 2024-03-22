package com.qrbats.qrbats.mobileauthentication.services;

import com.qrbats.qrbats.mobileauthentication.dto.StudentJwtAuthenticationResponse;
import com.qrbats.qrbats.mobileauthentication.dto.StudentRefreshTokenRequest;
import com.qrbats.qrbats.mobileauthentication.dto.StudentSignUpRequest;
import com.qrbats.qrbats.mobileauthentication.dto.StudentSigninRequest;
import com.qrbats.qrbats.mobileauthentication.entities.student.Student;

public interface MobileAuthenticationService {
    Student signup(StudentSignUpRequest studentSignUpRequest);
    boolean checkStudentIsExist(String email);
    boolean checkIndexNoIsExist(String indexNo);
    boolean checkUserNameIsExist(String userName);
    StudentJwtAuthenticationResponse signin(StudentSigninRequest studentSigninRequest);

    StudentJwtAuthenticationResponse refreshToken(StudentRefreshTokenRequest refreshTokenRequest);
}
