package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Billing;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class BillingController {

    public ResponseEntity<List<Billing>> getAllBills() {
        return null;
    }

    public ResponseEntity<Integer> createBill(Billing billing) {
        return null;
    }

    public ResponseEntity<Integer> deleteBill(Billing billing) {
        return null;
    }

    public ResponseEntity<List<Billing>> getBillsByBillingID(int billingId) {
        return null;
    }

    public ResponseEntity<List<Billing>> getBillsByPatient(int patientId) {
        return null;
    }
}
