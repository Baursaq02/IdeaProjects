package com.example.lab6.seeder;

import com.example.lab6.entity.*;
import com.example.lab6.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public void run(String... args) {
        if (studentRepository.count() == 0) {
            studentRepository.saveAll(List.of(
                    new Student(null, "Арман", 20, "arman@mail.com", "A01", LocalDateTime.now()),
                    new Student(null, "Аяжан", 22, "ayzhan@mail.com", "A02", LocalDateTime.now()),
                    new Student(null, "Данияр", 19, "daniyar@mail.com", "A01", LocalDateTime.now()),
                    new Student(null, "Жанбота", 23, "zhanbota@mail.com", "A03", LocalDateTime.now()),
                    new Student(null, "Медет", 21, "medet@mail.com", "A02", LocalDateTime.now())
            ));
        }

        if (courseRepository.count() == 0) {
            courseRepository.saveAll(List.of(
                    new Course(null, "Математика", 3),
                    new Course(null, "Физика", 4),
                    new Course(null, "Информатика", 5),
                    new Course(null, "Английский", 2),
                    new Course(null, "История Казахстана", 3)
            ));
        }
        if (enrollmentRepository.count() == 0) {
            enrollmentRepository.saveAll(List.of(
                    new Enrollment(null, 1L, 1L, LocalDateTime.now()),
                    new Enrollment(null, 2L, 2L, LocalDateTime.now()),
                    new Enrollment(null, 3L, 1L, LocalDateTime.now()),
                    new Enrollment(null, 4L, 3L, LocalDateTime.now())
            ));
        }

    }
}
