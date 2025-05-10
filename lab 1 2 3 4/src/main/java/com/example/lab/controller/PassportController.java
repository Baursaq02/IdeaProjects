package com.example.lab.controller;

import com.example.lab.model.Passport;
import com.example.lab.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passports")
public class PassportController {

    private final PassportService passportService;

    @Autowired
    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @GetMapping
    public List<Passport> getAllPassports() {
        return passportService.getAllPassports();
    }

    @PostMapping
    public Passport createPassport(@RequestBody Passport passport) {
        return passportService.createPassport(passport);
    }

    // 🔹 Обновление паспорта
    @PutMapping("/{id}")
    public ResponseEntity<Passport> updatePassport(
            @PathVariable Long id,
            @RequestBody Passport updatedPassport) {
        return ResponseEntity.ok(passportService.updatePassport(id, updatedPassport));
    }


    // 🔹 Удаление паспорта
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassport(@PathVariable Long id) {
        passportService.deletePassport(id);
        return ResponseEntity.noContent().build();
    }
}
