package com.edutech.progressive.service;
 
import com.edutech.progressive.entity.Patient;
 
import java.util.List;
 
public interface PatientService {
    List<Patient> getAllPatients() throws Exception;
    Integer addPatient(Patient patient) throws Exception;
    void updatePatient(Patient patient) throws Exception;
    void deletePatient(int patientId) throws Exception;
    Patient getPatientById(int patientId) throws Exception;
    List<Patient> getAllPatientSortedByName() throws Exception;
 
}