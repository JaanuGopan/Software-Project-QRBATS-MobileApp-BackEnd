package com.qrbats.qrbats.mobileauthentication.dto;

import com.qrbats.qrbats.mobileauthentication.entities.student.StudentRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentSignUpRequest {
    private String studentName;
    private String indexNumber;
    private String studentEmail;
    private String userName;
    private String password;
    private StudentRole studentRole;
    private Integer departmentId;
    private Integer currentSemester;
}
