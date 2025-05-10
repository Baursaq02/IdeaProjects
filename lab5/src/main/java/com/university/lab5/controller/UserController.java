package com.university.lab5.controller;

import com.university.lab5.entity.User;
import com.university.lab5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (user.getRole() == null) {
            user.setRole("STUDENT"); // Роль по умолчанию
        }
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/students/create")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<User> createStudent(@RequestBody User student) {
        return ResponseEntity.ok(userService.createStudent(student));
    }

    @GetMapping("/enrollments/my")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> getStudentEnrollments(Principal principal) {
        return ResponseEntity.ok("Здесь будут курсы студента: " + principal.getName());
    }
}
