package com.qrbats.qrbats.mobileauthentication.services.impl;

import com.qrbats.qrbats.mobileauthentication.dto.StudentJwtAuthenticationResponse;
import com.qrbats.qrbats.mobileauthentication.dto.StudentRefreshTokenRequest;
import com.qrbats.qrbats.mobileauthentication.dto.StudentSignUpRequest;
import com.qrbats.qrbats.mobileauthentication.dto.StudentSigninRequest;
import com.qrbats.qrbats.mobileauthentication.entities.otp.OTP;
import com.qrbats.qrbats.mobileauthentication.entities.otp.otpverification.dto.OTPRequest;
import com.qrbats.qrbats.mobileauthentication.entities.otp.repository.OTPRepository;
import com.qrbats.qrbats.mobileauthentication.entities.student.Student;
import com.qrbats.qrbats.mobileauthentication.entities.student.StudentRole;
import com.qrbats.qrbats.mobileauthentication.entities.student.repository.StudentRepository;
import com.qrbats.qrbats.mobileauthentication.services.JWTService;
import com.qrbats.qrbats.mobileauthentication.services.MobileAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MobileAuthenticationServicesImpl implements MobileAuthenticationService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final OTPRepository otpRepository;
    @Override
    public Student signup(StudentSignUpRequest studentSignUpRequest) {
        if(!checkStudentIsExist(studentSignUpRequest.getStudentEmail())){
            Student student = new Student();
            student.setStudentEmail(studentSignUpRequest.getStudentEmail());
            student.setStudentName(studentSignUpRequest.getStudentName());
            student.setIndexNumber(studentSignUpRequest.getIndexNumber());
            student.setDepartmentId(studentSignUpRequest.getDepartmentId());
            student.setStudentRole(StudentRole.UORSTUDENT);
            student.setCurrentSemester(studentSignUpRequest.getCurrentSemester());
            student.setUserName(studentSignUpRequest.getUserName());
            student.setPassword(passwordEncoder.encode(studentSignUpRequest.getPassword()));

            return studentRepository.save(student);
        }else {
            throw new RuntimeException("This Student is already exist.");
        }
    }

    @Override
    public boolean checkStudentIsExist(String email) {
        Optional<Student> oldStudent = studentRepository.findByStudentEmail(email);
        System.out.println(oldStudent);
        return oldStudent.isPresent();
    }

    @Override
    public boolean checkIndexNoIsExist(String indexNo) {
        Optional<Student> oldStudent = studentRepository.findByIndexNumber(indexNo);
        return oldStudent.isPresent();
    }

    @Override
    public boolean checkUserNameIsExist(String userName) {
        Optional<Student> oldStudent = studentRepository.findByUserName(userName);
        return oldStudent.isPresent();
    }

    @Override
    public StudentJwtAuthenticationResponse signin(StudentSigninRequest studentSigninRequest) {
        System.out.println("qwerty");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        studentSigninRequest.getStudentUserName(),
                        studentSigninRequest.getPassword()
                )
        );
        System.out.println("student");

        var student = studentRepository.findByUserName(
                studentSigninRequest.getStudentUserName()).orElseThrow(
                () -> new IllegalArgumentException("Invalid userName or password")
        );

        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("studentName",student.getStudentName());
        extraClaims.put("studentId",student.getStudentId());
        extraClaims.put("indexNumber",student.getIndexNumber());
        extraClaims.put("studentEmail", student.getStudentEmail());
        extraClaims.put("currentSemester",student.getCurrentSemester());
        extraClaims.put("departmentId",student.getDepartmentId());
        extraClaims.put("studentRole",student.getStudentRole());

        var jwt = jwtService.generateToken(student,extraClaims);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), student);

        StudentJwtAuthenticationResponse jwtAuthenticationResponse = new StudentJwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Override
    public StudentJwtAuthenticationResponse refreshToken(StudentRefreshTokenRequest refreshTokenRequest) {
        String userName = jwtService.extractUserName(refreshTokenRequest.getToken());
        Student student = studentRepository.findByUserName(userName).orElseThrow();

        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),student)){

            Map<String, Object> extraClaims = new HashMap<>();

            extraClaims.put("studentName",student.getStudentName());
            extraClaims.put("studentId",student.getStudentId());
            extraClaims.put("indexNumber",student.getIndexNumber());
            extraClaims.put("studentEmail", student.getStudentEmail());
            extraClaims.put("currentSemester",student.getCurrentSemester());
            extraClaims.put("departmentId",student.getDepartmentId());
            extraClaims.put("studentRole",student.getStudentRole());

            var jwt = jwtService.generateToken(student,extraClaims);

            StudentJwtAuthenticationResponse jwtAuthenticationResponse = new StudentJwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;

        }
        return null;
    }




}
