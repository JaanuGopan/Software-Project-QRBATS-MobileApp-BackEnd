package com.qrbats.qrbats.mobileauthentication.controller;

import com.qrbats.qrbats.mobileauthentication.dto.*;
import com.qrbats.qrbats.mobileauthentication.entities.student.Student;
import com.qrbats.qrbats.mobileauthentication.services.MobileAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mobile")
@RequiredArgsConstructor
public class StudentAuthenticationController {

    private final MobileAuthenticationService mobileAuthenticationService;

    @PostMapping("/signup")
    public ResponseEntity<Student> signup(@RequestBody StudentSignUpRequest signUpRequest){
        return ResponseEntity.ok(mobileAuthenticationService.signup(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<StudentJwtAuthenticationResponse> signin(@RequestBody StudentSigninRequest signinRequest){
        return ResponseEntity.ok(mobileAuthenticationService.signin(signinRequest));
    }

    @PostMapping("/checkstudentemail")
    public ResponseEntity<Boolean> checkStudentEmailIsExist(@RequestBody StudentCheckStudentEmailRequest studentCheckStudentEmailRequest){
        return ResponseEntity.ok(mobileAuthenticationService.checkStudentIsExist(studentCheckStudentEmailRequest.getStudentEmail()));
    }

    @PostMapping("/checkstudentindexno")
    public ResponseEntity<Boolean> checkStudentIndexNoIsExist(@RequestBody StudentCheckStudentIndexNoRequest studentCheckStudentIndexNoRequest){
        return ResponseEntity.ok(mobileAuthenticationService.checkIndexNoIsExist(studentCheckStudentIndexNoRequest.getStudentIndexNo()));
    }

    @PostMapping("/checkstudentusername")
    public ResponseEntity<Boolean> checkStudentUserNameIsExist(@RequestBody StudentCheckUserNameRequest studentCheckUserNameRequest){
        return ResponseEntity.ok(mobileAuthenticationService.checkUserNameIsExist(studentCheckUserNameRequest.getStudentUserName()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<StudentJwtAuthenticationResponse> refresh(@RequestBody StudentRefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(mobileAuthenticationService.refreshToken(refreshTokenRequest));
    }



}
