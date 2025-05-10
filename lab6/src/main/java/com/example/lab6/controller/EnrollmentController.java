package com.example.lab6.controller;

import com.example.lab6.entity.Enrollment;
import com.example.lab6.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository repo;

    @GetMapping
    public ResponseEntity<?> getEnrollments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Sort.Direction dir = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
        Page<Enrollment> result = repo.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", result.getContent());
        response.put("page", result.getNumber());
        response.put("size", result.getSize());
        response.put("totalElements", result.getTotalElements());
        response.put("totalPages", result.getTotalPages());
        response.put("last", result.isLast());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
        Enrollment saved = repo.save(enrollment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterEnrollments(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String enrollmentDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        List<Enrollment> filtered = repo.findAll();

        if (studentId != null) {
            filtered = filtered.stream()
                    .filter(e -> Objects.equals(e.getStudentId(), studentId))
                    .toList();
        }

        if (courseId != null) {
            filtered = filtered.stream()
                    .filter(e -> Objects.equals(e.getCourseId(), courseId))
                    .toList();
        }

        if (enrollmentDate != null) {
            LocalDate date = LocalDate.parse(enrollmentDate);
            filtered = filtered.stream()
                    .filter(e -> e.getEnrollmentDate().toLocalDate().equals(date))
                    .toList();
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        Page<Enrollment> pageResult = new PageImpl<>(filtered.subList(start, end), pageable, filtered.size());

        Map<String, Object> response = new HashMap<>();
        response.put("content", pageResult.getContent());
        response.put("totalElements", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        Map<String, Object> filters = new HashMap<>();
        if (studentId != null) filters.put("studentId", String.valueOf(studentId));
        if (courseId != null) filters.put("courseId", String.valueOf(courseId));
        if (enrollmentDate != null) filters.put("enrollmentDate", enrollmentDate);

        response.put("filtersApplied", filters);

        return ResponseEntity.ok(response);
    }
}
