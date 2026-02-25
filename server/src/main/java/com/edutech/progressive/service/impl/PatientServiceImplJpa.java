package com.edutech.progressive.service.impl;
 
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.PatientService;
import org.springframework.stereotype.Service;
 
import java.util.Collections;
import java.util.List;
 
@Service("patientServiceImplJpa")
public class PatientServiceImplJpa implements PatientService {
 
    @Override
    public List<Patient> getAllPatients() throws Exception {
        return Collections.emptyList();
    }
 
    @Override
    public Patient getPatientById(int patientId) throws Exception {
        return null;
    }
 
    @Override
    public Integer addPatient(Patient patient) throws Exception {
        return -1;
    }
 
    @Override
    public void updatePatient(Patient patient) throws Exception {
    }
 
    @Override
    public void deletePatient(int patientId) throws Exception {
    }
 
    @Override
    public List<Patient> getAllPatientSortedByName() throws Exception {
        return Collections.emptyList();
    }
}