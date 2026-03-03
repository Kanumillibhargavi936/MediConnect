package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.exception.ClinicAlreadyExistsException;
import com.edutech.progressive.service.ClinicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/clinic")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    // GET /clinic
    @GetMapping
    public ResponseEntity<List<Clinic>> getAllClinics() {
        List<Clinic> clinics = clinicService.getAllClinics();
        return ResponseEntity.ok(clinics);
    }

    // GET /clinic/{clinicId}
    @GetMapping("/{clinicId}")
    public ResponseEntity<Clinic> getClinicById(@PathVariable int clinicId) {
        // Day-8 tests expect 200 + null when not found
        Clinic clinic = clinicService.getClinicById(clinicId);
        return ResponseEntity.ok(clinic);
    }

    // POST /clinic
    @PostMapping
    public ResponseEntity<?> addClinic(@RequestBody Clinic clinic) {
        try {
            Integer id = clinicService.addClinic(clinic);
            Clinic saved = clinicService.getClinicById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (ClinicAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating clinic: " + e.getMessage());
        }
    }

    // PUT /clinic/{clinicId}
    @PutMapping("/{clinicId}")
    public ResponseEntity<?> updateClinic(@PathVariable int clinicId, @RequestBody Clinic clinic) {
        try {
            clinic.setClinicId(clinicId);
            clinicService.updateClinic(clinic);
            Clinic updated = clinicService.getClinicById(clinicId);
            return ResponseEntity.ok(updated);
        } catch (ClinicAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating clinic: " + e.getMessage());
        }
    }

    // DELETE /clinic/{clinicId}
    @DeleteMapping("/{clinicId}")
    public ResponseEntity<?> deleteClinic(@PathVariable int clinicId) {
        try {
            clinicService.deleteClinic(clinicId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Clinic deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting clinic: " + e.getMessage());
        }
    }

    // GET /clinic/location/{location}
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Clinic>> getAllClinicByLocation(@PathVariable String location) {
        return ResponseEntity.ok(clinicService.getAllClinicByLocation(location));
    }

    // GET /clinic/doctor/{doctorId}
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Clinic>> getAllClinicByDoctorId(@PathVariable int doctorId) {
        return ResponseEntity.ok(clinicService.getAllClinicByDoctorId(doctorId));
    }
}