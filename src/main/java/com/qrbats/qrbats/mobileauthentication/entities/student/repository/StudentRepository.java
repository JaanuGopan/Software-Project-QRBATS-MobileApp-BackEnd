package com.qrbats.qrbats.mobileauthentication.entities.student.repository;

import com.qrbats.qrbats.mobileauthentication.entities.student.Student;
import com.qrbats.qrbats.mobileauthentication.entities.student.StudentRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByUserName(String userName);

    Optional<Student> findByStudentEmail(String email);
    Optional<Student> findByIndexNumber(String indexNumber);


}
