package com.example.lab6.repository;

import com.example.lab6.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTitle(String title);
    List<Course> findByCreditHours(Integer creditHours);
    List<Course> findByTitleContaining(String keyword);
}
