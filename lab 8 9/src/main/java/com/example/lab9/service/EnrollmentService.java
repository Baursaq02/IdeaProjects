package com.example.lab9.service;

import com.example.lab9.entity.Course;
import com.example.lab9.entity.Enrollment;
import com.example.lab9.entity.Student;
import com.example.lab9.repository.CourseRepository;
import com.example.lab9.repository.EnrollmentRepository;
import com.example.lab9.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EmailService emailService;

    public Enrollment enrollStudent(Long studentId, Long courseId) {
        System.out.println("Получаю студента с ID = " + studentId);
        System.out.println("Получаю курс с ID = " + courseId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("❌ Студент не найден"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("❌ Курс не найден"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        Enrollment saved = enrollmentRepository.save(enrollment);

        System.out.println("✅ Сохранил запись enrollment");
        System.out.println("📧 Отправляю письмо на " + student.getEmail());

        String subject = "Enrollment Confirmation";
        String body = String.format("Dear %s,\n\nYou have been enrolled in \"%s\" starting from %s.\n\nBest regards,",
                student.getName(), course.getTitle(), saved.getEnrollmentDate().toLocalDate());

        emailService.sendSimpleMessage(student.getEmail(), subject, body);

        System.out.println("✅ Письмо отправлено");

        return saved;
    }

}

