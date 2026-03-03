package com.edutech.progressive.service.impl;
 
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.exception.PatientAlreadyExistsException;
import com.edutech.progressive.exception.PatientNotFoundException;
import com.edutech.progressive.repository.PatientRepository;
import com.edutech.progressive.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
@Primary
@Service
public class PatientServiceImplJpa implements PatientService {
 
    private final PatientRepository patientRepository;
 
    /** For Spring autowiring */
    @Autowired
    public PatientServiceImplJpa(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
 
    @Override
    public List<Patient> getAllPatients() throws Exception {
        return patientRepository.findAll();
    }
 
    @Override
    public Patient getPatientById(int patientId) throws Exception {
        // Day-9: must throw PatientNotFoundException
        return patientRepository.findByPatientId(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + patientId));
    }
 
    @Override
    public Integer addPatient(Patient patient) throws Exception {
        // Day-9: email uniqueness
        if (patient.getEmail() != null &&
            patientRepository.findByEmail(patient.getEmail()).isPresent()) {
            throw new PatientAlreadyExistsException("Patient already exists with email: " + patient.getEmail());
        }
        Patient saved = patientRepository.save(patient);
        return saved.getPatientId();
    }
 
    @Override
    public void updatePatient(Patient patient) throws Exception {
        if (patient.getPatientId() <= 0) {
            throw new PatientNotFoundException("Patient id is required for update");
        }
 
        Patient existing = patientRepository.findByPatientId(patient.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + patient.getPatientId()));
 
        // Email uniqueness if changed
        if (patient.getEmail() != null && !patient.getEmail().equals(existing.getEmail())) {
            if (patientRepository.findByEmail(patient.getEmail()).isPresent()) {
                throw new PatientAlreadyExistsException("Another patient already exists with email: " + patient.getEmail());
            }
            existing.setEmail(patient.getEmail());
        }
 
        existing.setFullName(patient.getFullName());
        existing.setDateOfBirth(patient.getDateOfBirth());
        existing.setContactNumber(patient.getContactNumber());
        existing.setAddress(patient.getAddress());
 
        patientRepository.save(existing);
    }
 
    @Override
    public void deletePatient(int patientId) throws Exception {
        // Idempotent delete: no throw if missing
        patientRepository.findByPatientId(patientId).ifPresent(patientRepository::delete);
    }
 
    @Override
    public List<Patient> getAllPatientSortedByName() throws Exception {
        List<Patient> list = patientRepository.findAll();
        list.sort((a, b) -> a.getFullName().compareToIgnoreCase(b.getFullName()));
        return list;
    }
}