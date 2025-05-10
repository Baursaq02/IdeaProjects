package com.example.lab6.controller;

import com.example.lab6.entity.Student;
import com.example.lab6.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository repo;

    @GetMapping
    public ResponseEntity<?> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort
    ) {
        Sort.Direction dir = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort[0]));
        Page<Student> result = repo.findAll(pageable);

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
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = repo.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String groupName,
            @RequestParam(required = false, name = "name_like") String nameLike,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        List<Student> filtered = repo.findAll();

        if (name != null) {
            filtered = filtered.stream()
                    .filter(s -> s.getName() != null && s.getName().equalsIgnoreCase(name))
                    .toList();
        }

        if (email != null) {
            filtered = filtered.stream()
                    .filter(s -> s.getEmail() != null && s.getEmail().equalsIgnoreCase(email))
                    .toList();
        }

        if (groupName != null) {
            filtered = filtered.stream()
                    .filter(s -> s.getGroupName() != null && s.getGroupName().equalsIgnoreCase(groupName))
                    .toList();
        }

        if (nameLike != null) {
            filtered = filtered.stream()
                    .filter(s -> s.getName() != null && s.getName().toLowerCase().contains(nameLike.toLowerCase()))
                    .toList();
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        Page<Student> pageResult = new PageImpl<>(filtered.subList(start, end), pageable, filtered.size());

        Map<String, Object> response = new HashMap<>();
        response.put("content", pageResult.getContent());
        response.put("totalElements", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());

        Map<String, String> filters = new HashMap<>();
        if (name != null) filters.put("name", name);
        if (email != null) filters.put("email", email);
        if (groupName != null) filters.put("groupName", groupName);
        if (nameLike != null) filters.put("name_like", nameLike);

        response.put("filtersApplied", filters);

        return ResponseEntity.ok(response);
    }
}
