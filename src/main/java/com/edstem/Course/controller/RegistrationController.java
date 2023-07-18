package com.edstem.Course.controller;

import com.edstem.Course.contract.Registrations;
import com.edstem.Course.model.Registration;
import com.edstem.Course.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public ResponseEntity<List<Registrations>> getAllRegistrations() {
        List<Registrations> registrations = registrationService.getAllRegistrations();
        if (registrations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registrations> getRegistrationById(@PathVariable Long id) {
        Registrations registration = registrationService.getRegistrationById(id);
        return ResponseEntity.ok(registration);
    }

    @PostMapping
    public ResponseEntity<Registrations> addRegistration(@RequestBody Registration registration) {
        Registrations addedRegistration = registrationService.addRegistration(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRegistration);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registrations> updateRegistrationById(@PathVariable Long id, @RequestBody Registration updatedRegistration) {
        Registrations updatedRegistrations = registrationService.updateRegistrationById(id, updatedRegistration);
        return ResponseEntity.ok(updatedRegistrations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistrationById(@PathVariable Long id) {
        registrationService.deleteRegistrationById(id);
        return ResponseEntity.ok().build();
    }
}
