package com.edutech.progressive.controller;
import java.util.List;
 
import java.util.Optional;
import org.springframework.http.ResponseEntity;
 
import org.springframework.web.bind.annotation.*;
import com.edutech.progressive.entity.Patient;
 
import com.edutech.progressive.repository.PatientRepository;
 
import com.edutech.progressive.service.impl.PatientServiceImplJpa;
@RestController
 
@RequestMapping("/patient")
 
public class PatientController {
    private final PatientServiceImplJpa service;
    public PatientController(PatientRepository repository) {
 
        this.service = new PatientServiceImplJpa(repository);
 
    }
    @GetMapping
 
    public List<Patient> getAllPatients() {
 
        return service.getAllPatients();
 
    }
    @GetMapping("/sorted")
 
    public List<Patient> getAllSorted() {
 
        return service.getAllPatientsSortedByName();
 
    }
    @PutMapping("/{id}")
 
    public Optional<Patient> update(@PathVariable Long id, @RequestBody Patient patient) {
 
        return service.updatePatient(id, patient);
 
    }
    @DeleteMapping("/{id}")
 
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
 
        return ResponseEntity.status(204).body(service.deletePatient(id));
 
    }
 
}