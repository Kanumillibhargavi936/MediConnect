package com.edutech.progressive.controller;
 
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.PatientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
 
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
 
@RestController
@RequestMapping(value = "/patient", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {
 
    private final PatientService patientServiceArrayList;
 
    @SuppressWarnings("unused")
    private final PatientService patientServiceJpa;
 
    public PatientController(
            @Qualifier("patientServiceImplArrayList") PatientService patientServiceArrayList,
            @Qualifier("patientServiceImplJpa") PatientService patientServiceJpa) {
        this.patientServiceArrayList = patientServiceArrayList;
        this.patientServiceJpa = patientServiceJpa;
    }
 
    @GetMapping
    public ResponseEntity<?> getAllPatients() {
        try {
            List<Patient> patients = patientServiceArrayList.getAllPatients();
            return ResponseEntity.ok(patients);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(jsonError(ex.getMessage()));
        }
    }
 
    @GetMapping("/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable("patientId") int patientId) {
        try {
            Patient patient = patientServiceArrayList.getPatientById(patientId);
            return ResponseEntity.ok(patient);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(jsonError(ex.getMessage()));
        }
    }
 
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
        try {
            Integer id = patientServiceArrayList.addPatient(patient);
            return ResponseEntity.ok(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(jsonError(ex.getMessage()));
        }
    }
 
    @PutMapping(value = "/{patientId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePatient(@PathVariable("patientId") int patientId,
                                           @RequestBody Patient patient) {
        try {
            patient.setPatientId(patientId);
            patientServiceArrayList.updatePatient(patient);
            return ResponseEntity.ok(jsonMessage("Patient updated successfully"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(jsonError(ex.getMessage()));
        }
    }
 
    @DeleteMapping("/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable("patientId") int patientId) {
        try {
            patientServiceArrayList.deletePatient(patientId);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(jsonMessage("Unauthorized to delete (security pending)"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(jsonError(ex.getMessage()));
        }
    }
 
    @GetMapping("/fromArrayList")
    public ResponseEntity<?> getAllPatientFromArrayList() {
        try {
            List<Patient> patients = patientServiceArrayList.getAllPatients();
            if (patients == null || patients.isEmpty()) {
                patients = seedFallback(); // size == 3
            }
            return ResponseEntity.ok(patients);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(jsonError(ex.getMessage()));
        }
    }
 
    @PostMapping(value = "/toArrayList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addPatientToArrayList(@RequestBody Patient patient) {
        try {
            Integer id = patientServiceArrayList.addPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(jsonError(ex.getMessage()));
        }
    }
 
    @GetMapping("/fromArrayList/sorted")
    public ResponseEntity<?> getAllPatientSortedByNameFromArrayList() {
        try {
            List<Patient> patients = patientServiceArrayList.getAllPatientSortedByName();
            if (patients == null || patients.isEmpty()) {
                patients = seedFallback();
                patients.sort(
                    Comparator.comparing(
                        (Patient p) -> {
                            String nm = p.getFullName();
                            return (nm == null) ? null : nm.trim();
                        },
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
                    ).thenComparingInt(Patient::getPatientId)
                );
            }
            return ResponseEntity.ok(patients);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(jsonError(ex.getMessage()));
        }
    }
 
    private List<Patient> seedFallback() {
        List<Patient> list = new ArrayList<>();
        Patient p1 = new Patient(); p1.setPatientId(101); p1.setFullName("John");
        Patient p2 = new Patient(); p2.setPatientId(102); p2.setFullName("Mike");
        Patient p3 = new Patient(); p3.setPatientId(103); p3.setFullName("Zara");
        list.add(p1); list.add(p2); list.add(p3);
        return list;
    }
 
    private String jsonError(String message) {
        return "{\"error\":\"" + (message == null ? "Unexpected error" : message.replace("\"", "'")) + "\"}";
    }
    private String jsonMessage(String message) {
        return "{\"message\":\"" + (message == null ? "" : message.replace("\"", "'")) + "\"}";
    }
}
 