package com.example.lab.service;

import com.example.lab.model.University;
import com.example.lab.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityService {

    private final UniversityRepository universityRepository;

    @Autowired
    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    public University getUniversityById(Long id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
    }

    public University createUniversity(University university) {
        return universityRepository.save(university);
    }

    public University updateUniversity(Long id, University updatedUniversity) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));

        university.setName(updatedUniversity.getName());
        return universityRepository.save(university);
    }

    public void deleteUniversity(Long id) {
        if (!universityRepository.existsById(id)) {
            throw new RuntimeException("University not found");
        }
        universityRepository.deleteById(id);
    }
}
