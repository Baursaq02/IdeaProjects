package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.Passport;
import com.example.hotelbooking.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passports")
public class PassportController {
    @Autowired
    private PassportRepository passportRepository;

    @GetMapping
    public ResponseEntity<List<Passport>> getAllPassports() {
        List<Passport> passports = passportRepository.findAll();
        return ResponseEntity.ok(passports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passport> getPassportById(@PathVariable Long id) {
        Optional<Passport> passport = passportRepository.findById(id);
        return passport.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Passport> createPassport(@RequestBody Passport passport) {
        Passport savedPassport = passportRepository.save(passport);
        return ResponseEntity.ok(savedPassport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passport> updatePassport(@PathVariable Long id, @RequestBody Passport updatedPassport) {
        Optional<Passport> existingPassport = passportRepository.findById(id);
        if (existingPassport.isPresent()) {
            updatedPassport.setId(id);
            return ResponseEntity.ok(passportRepository.save(updatedPassport));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassport(@PathVariable Long id) {
        if (passportRepository.existsById(id)) {
            passportRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
