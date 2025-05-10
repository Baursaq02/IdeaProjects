package com.example.lab6.repository;

import com.example.lab6.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByName(String name);

    List<Student> findByEmail(String email);

    List<Student> findByGroupName(String groupName);

    List<Student> findByNameContaining(String name);

}
