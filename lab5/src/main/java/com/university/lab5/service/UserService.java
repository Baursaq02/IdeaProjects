package com.university.lab5.service;

import com.university.lab5.entity.User;
import com.university.lab5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User registerUser(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Хешируем пароль
        return userRepository.save(user);
    }

    @Transactional
    public User createStudent(User student) {
        Optional<User> existingUser = userRepository.findByUsername(student.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Студент с таким именем уже существует");
        }
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole("STUDENT");
        return userRepository.save(student);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
