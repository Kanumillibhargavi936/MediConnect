package com.edutech.progressive.service;

import com.edutech.progressive.entity.Billing;

import java.util.List;

public interface BillingService {

    List<Billing> getAllBills()throws Exception;

    Billing getBillById(int billingId)throws Exception;

    Integer createBill(Billing billing)throws Exception;

    void deleteBill(int billingId)throws Exception;

    List<Billing> getBillsByPatientId(int patientId)throws Exception;
}

