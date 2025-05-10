package com.example.lab6.controller;

import com.example.lab6.entity.Course;
import com.example.lab6.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository repo;

    @GetMapping
    public ResponseEntity<?> getCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title,asc") String[] sort
    ) {
        Sort.Direction dir = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
        Page<Course> result = repo.findAll(pageable);

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
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course saved = repo.save(course);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterCourses(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, name = "title_like") String titleLike,
            @RequestParam(required = false) Integer creditHours,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<Course> filtered = repo.findAll();

        if (title != null) {
            filtered = filtered.stream()
                    .filter(c -> c.getTitle() != null && c.getTitle().equalsIgnoreCase(title))
                    .toList();
        }

        if (titleLike != null) {
            filtered = filtered.stream()
                    .filter(c -> c.getTitle() != null && c.getTitle().toLowerCase().contains(titleLike.toLowerCase()))
                    .toList();
        }

        if (creditHours != null) {
            filtered = filtered.stream()
                    .filter(c -> Objects.equals(c.getCreditHours(), creditHours))
                    .toList();
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        Page<Course> pageResult = new PageImpl<>(filtered.subList(start, end), pageable, filtered.size());

        Map<String, Object> response = new HashMap<>();
        response.put("content", pageResult.getContent());
        response.put("totalElements", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        Map<String, Object> filters = new HashMap<>();
        if (title != null) filters.put("title", title);
        if (titleLike != null) filters.put("title_like", titleLike);
        if (creditHours != null) filters.put("creditHours", creditHours.toString());

        response.put("filtersApplied", filters);

        return ResponseEntity.ok(response);
    }
}
