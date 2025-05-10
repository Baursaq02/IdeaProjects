package com.example.lab.service;

import com.example.lab.model.Passport;
import com.example.lab.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassportService {

    private final PassportRepository passportRepository;

    @Autowired
    public PassportService(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    public List<Passport> getAllPassports() {
        return passportRepository.findAll();
    }

    public Passport createPassport(Passport passport) {
        return passportRepository.save(passport);
    }

    public Passport updatePassport(Long id, Passport updatedPassport) {
        Passport passport = passportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passport not found"));

        passport.setNumber(updatedPassport.getNumber());

        if (updatedPassport.getStudent() != null) {
            passport.setStudent(updatedPassport.getStudent());
        }

        return passportRepository.save(passport);
    }


    public void deletePassport(Long id) {
        if (!passportRepository.existsById(id)) {
            throw new RuntimeException("Passport not found");
        }
        passportRepository.deleteById(id);
    }
}
