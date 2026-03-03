package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Billing;
import com.edutech.progressive.service.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBills() {
        try {
            return ResponseEntity.ok(billingService.getAllBills());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching bills: " + e.getMessage());
        }
    }

    @GetMapping("/{billingId}")
    public ResponseEntity<?> getBillById(@PathVariable int billingId) {
        try {
            return ResponseEntity.ok(billingService.getBillById(billingId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching bill: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createBill(@RequestBody Billing billing) {
        try {
            Integer id = billingService.createBill(billing);
            Billing saved = billingService.getBillById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating bill: " + e.getMessage());
        }
    }

    @PutMapping("/{billingId}")
    public ResponseEntity<?> updateBill(@PathVariable int billingId, @RequestBody Billing billing) {
        try {
            billing.setBillingId(billingId);
            billingService.updateBill(billing);
            Billing updated = billingService.getBillById(billingId);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating bill: " + e.getMessage());
        }
    }

    @DeleteMapping("/{billingId}")
    public ResponseEntity<?> deleteBill(@PathVariable int billingId) {
        try {
            billingService.deleteBill(billingId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Bill deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting bill: " + e.getMessage());
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getBillsByPatient(@PathVariable int patientId) {
        try {
            return ResponseEntity.ok(billingService.getBillsByPatientId(patientId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching bills by patient: " + e.getMessage());
        }
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<?> getBillsByClinic(@PathVariable int clinicId) {
        try {
            return ResponseEntity.ok(billingService.getBillsByClinicId(clinicId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching bills by clinic: " + e.getMessage());
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> getBillsByAppointment(@PathVariable int appointmentId) {
        try {
            return ResponseEntity.ok(billingService.getBillsByAppointmentId(appointmentId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching bills by appointment: " + e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getBillsByStatus(@PathVariable String status) {
        try {
            return ResponseEntity.ok(billingService.getBillsByStatus(status));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching bills by status: " + e.getMessage());
        }
    }
}