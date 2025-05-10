package com.university.lab5.controller;

import com.university.lab5.dto.EnrollmentRequest;
import com.university.lab5.entity.Enrollment;
import com.university.lab5.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<String> enrollStudent(@RequestBody EnrollmentRequest request) {
        enrollmentService.enrollStudent(request.getStudentId(), request.getCourseId());
        return ResponseEntity.ok("Enrollment successful");
    }

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getStudentEnrollments(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudent(studentId));
    }
}
