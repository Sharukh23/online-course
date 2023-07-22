package com.edstem.course.controller;

import com.edstem.course.contract.Registration;
import com.edstem.course.service.RegistrationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        List<Registration> registrations = registrationService.getAllRegistrations();
        if (registrations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable Long id) {
        Registration registration = registrationService.getRegistrationById(id);
        return ResponseEntity.ok(registration);
    }

    @PostMapping
    public ResponseEntity<Registration> addRegistration(
            @Valid @RequestBody Registration registration) {
        Registration registrationDto = registrationService.addRegistration(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registration> updateRegistrationById(
            @PathVariable Long id, @Valid @RequestBody Registration updatedRegistration) {
        Registration updatedRegistrations =
                registrationService.updateRegistrationById(id, updatedRegistration);
        return ResponseEntity.ok(updatedRegistrations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistrationById(@PathVariable Long id) {
        Long studentId = registrationService.deleteRegistrationById(id);
        return ResponseEntity.ok(
                "Registration with Id "
                        + id
                        + " and student id "
                        + studentId
                        + " in it has been deleted");
    }
}
