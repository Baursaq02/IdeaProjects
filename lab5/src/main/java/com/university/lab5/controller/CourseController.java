package com.university.lab5.controller;

import com.university.lab5.entity.Course;
import com.university.lab5.entity.User;
import com.university.lab5.service.CourseService;
import com.university.lab5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        // Проверяем, существует ли учитель в базе данных
        Optional<User> teacher = userService.findById(course.getTeacher().getId());
        if (teacher.isEmpty() || !"TEACHER".equals(teacher.get().getRole())) {
            return ResponseEntity.badRequest().body("Teacher with ID " + course.getTeacher().getId() + " not found or is not a teacher.");
        }

        // Сохраняем курс, привязав учителя
        course.setTeacher(teacher.get());
        Course savedCourse = courseService.createCourse(course);
        return ResponseEntity.ok(savedCourse);
    }
}
