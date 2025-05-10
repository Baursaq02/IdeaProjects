package com.university.lab5.service;

import com.university.lab5.entity.Course;
import com.university.lab5.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        // Проверяем, существует ли уже такой курс
        Optional<Course> existingCourse = courseRepository.findByName(course.getName());
        if (existingCourse.isPresent()) {
            throw new IllegalArgumentException("Course with name " + course.getName() + " already exists.");
        }
        return courseRepository.save(course);
    }
}
