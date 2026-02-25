package com.edutech.progressive.service.impl;
 
import com.edutech.progressive.dao.PatientDAO;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.PatientService;
 
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
 
public class PatientServiceImplJdbc implements PatientService {
 
    private final PatientDAO patientDAO;
 
    public PatientServiceImplJdbc(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }
 
    @Override
    public List<Patient> getAllPatients() throws Exception {
        try {
            return patientDAO.getAllPatients();
        } catch (SQLException e) {
            throw new Exception("Failed to fetch patients", e);
        }
    }
 
    @Override
    public Integer addPatient(Patient patient) throws Exception {
        try {
            return patientDAO.addPatient(patient);
        } catch (SQLException e) {
            throw new Exception("Failed to add patient", e);
        }
    }
 
    @Override
    public List<Patient> getAllPatientSortedByName() throws Exception {
        try {
            List<Patient> all = patientDAO.getAllPatients(); // must be non-null
            return all.stream()
                      .sorted(Comparator.comparing(
                          p -> p.getFullName() == null ? "" : p.getFullName(),
                          String.CASE_INSENSITIVE_ORDER))
                      .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new Exception("Failed to fetch/sort patients by name", e);
        }
    }
 
    @Override
    public void updatePatient(Patient patient) throws Exception {
        try {
            patientDAO.updatePatient(patient);
        } catch (SQLException e) {
            throw new Exception("Failed to update patient", e);
        }
    }
 
    @Override
    public void deletePatient(int patientId) throws Exception {
        try {
            patientDAO.deletePatient(patientId);
        } catch (SQLException e) {
            throw new Exception("Failed to delete patient", e);
        }
    }
 
    @Override
    public Patient getPatientById(int patientId) throws Exception {
        try {
            return patientDAO.getPatientById(patientId);
        } catch (SQLException e) {
            throw new Exception("Failed to fetch patient by id: " + patientId, e);
        }
    }
}