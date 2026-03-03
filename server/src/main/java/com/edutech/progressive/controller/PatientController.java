package com.edutech.progressive.controller;
 
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.exception.PatientAlreadyExistsException;
import com.edutech.progressive.exception.PatientNotFoundException;
import com.edutech.progressive.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.Collections;
import java.util.List;
 
@RestController
@RequestMapping("/patient")
public class PatientController {
 
    private final PatientService patientService;
 
    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }
 
    @GetMapping
    public ResponseEntity<?> getAllPatients() {
        try {
            List<Patient> list = patientService.getAllPatients();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching patients: " + e.getMessage());
        }
    }
 
    @GetMapping("/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable int patientId) {
        try {
            Patient p = patientService.getPatientById(patientId);
            return ResponseEntity.ok(p);
        } catch (PatientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching patient: " + e.getMessage());
        }
    }
 
    @PostMapping
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
        try {
            Integer id = patientService.addPatient(patient);
            Patient saved = patientService.getPatientById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (PatientAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating patient: " + e.getMessage());
        }
    }
 
    @PutMapping("/{patientId}")
    public ResponseEntity<?> updatePatient(@PathVariable int patientId, @RequestBody Patient patient) {
        try {
            patient.setPatientId(patientId);
            patientService.updatePatient(patient);
            Patient updated = patientService.getPatientById(patientId);
            return ResponseEntity.ok(updated);
        } catch (PatientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PatientAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating patient: " + e.getMessage());
        }
    }
 
    @DeleteMapping("/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable int patientId) {
        try {
            patientService.deletePatient(patientId); // idempotent
            return ResponseEntity.ok(Collections.singletonMap("message", "Patient deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting patient: " + e.getMessage());
        }
    }
}