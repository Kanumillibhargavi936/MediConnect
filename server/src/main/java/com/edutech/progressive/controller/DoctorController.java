package com.edutech.progressive.controller;
 
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.exception.DoctorAlreadyExistsException;
import com.edutech.progressive.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.Collections;
import java.util.List;
 
@RestController
@RequestMapping("/doctor")
public class DoctorController {
 
    private final DoctorService doctorService;
 
    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }
 
    @GetMapping
    public ResponseEntity<?> getAllDoctors() {
        try {
            return ResponseEntity.ok(doctorService.getAllDoctors());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching doctors: " + e.getMessage());
        }
    }
 
    @GetMapping("/{doctorId}")
    public ResponseEntity<?> getDoctorById(@PathVariable int doctorId) {
        try {
            return ResponseEntity.ok(doctorService.getDoctorById(doctorId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching doctor: " + e.getMessage());
        }
    }
 
    @PostMapping
    public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor) {
        try {
            Integer id = doctorService.addDoctor(doctor);
            Doctor saved = doctorService.getDoctorById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DoctorAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating doctor: " + e.getMessage());
        }
    }
 
    @PutMapping("/{doctorId}")
    public ResponseEntity<?> updateDoctor(@PathVariable int doctorId, @RequestBody Doctor doctor) {
        try {
            doctor.setDoctorId(doctorId);
            doctorService.updateDoctor(doctor);
            Doctor updated = doctorService.getDoctorById(doctorId);
            return ResponseEntity.ok(updated);
        } catch (DoctorAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating doctor: " + e.getMessage());
        }
    }
 
    @DeleteMapping("/{doctorId}")
    public ResponseEntity<?> deleteDoctor(@PathVariable int doctorId) {
        try {
            doctorService.deleteDoctor(doctorId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Doctor deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting doctor: " + e.getMessage());
        }
    }
 
    @GetMapping("/experience")
    public ResponseEntity<?> getDoctorSortedByExperience() {
        try {
            return ResponseEntity.ok(doctorService.getDoctorSortedByExperience());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sorting doctors: " + e.getMessage());
        }
    }
}