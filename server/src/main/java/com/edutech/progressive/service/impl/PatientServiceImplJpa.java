package com.edutech.progressive.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.repository.PatientRepository;
import com.edutech.progressive.service.PatientService;
@Service
public class PatientServiceImplJpa implements PatientService {
    private PatientRepository repository;
    public PatientServiceImplJpa() {
    }
    public PatientServiceImplJpa(PatientRepository repository) {
        this.repository = repository;
    }
    public void setRepository(PatientRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<Patient> getAllPatients() {
        if (repository == null) return new ArrayList<>();
        return repository.findAll();
    }
    @Override
    public Integer addPatient(Patient patient) throws Exception {
        Patient saved = repository.save(patient);
        return saved.getPatientId();
    }
    @Override
    public List<Patient> getAllPatientSortedByName() {
        if (repository == null) return new ArrayList<>();
        return repository.findAll(Sort.by(Sort.Direction.ASC, "fullName"));
    }
    public List<Patient> getAllPatientsSortedByName() {
        return getAllPatientSortedByName();
    }
    @Override
    public void updatePatient(Patient patient) {
        if (repository == null || patient == null) return;
        int id = patient.getPatientId();
        Patient existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setFullName(patient.getFullName());
            existing.setDateOfBirth(patient.getDateOfBirth());
            existing.setContactNumber(patient.getContactNumber());
            existing.setEmail(patient.getEmail());
            existing.setAddress(patient.getAddress());
            repository.save(existing);
        }
    }
    public Optional<Patient> updatePatient(Long id, Patient patient) {
        if (repository == null || id == null || patient == null) return Optional.empty();
        int pid = id.intValue();
        Patient existing = repository.findById(pid).orElse(null);
        if (existing == null) return Optional.empty();
        existing.setFullName(patient.getFullName());
        existing.setDateOfBirth(patient.getDateOfBirth());
        existing.setContactNumber(patient.getContactNumber());
        existing.setEmail(patient.getEmail());
        existing.setAddress(patient.getAddress());
        Patient saved = repository.save(existing);
        return Optional.ofNullable(saved);
    }
    @Override
    public void deletePatient(int patientId) {
        if (repository == null) return;
        if (repository.existsById(patientId)) {
            repository.deleteById(patientId);
        }
    }
    public boolean deletePatient(Long patientId) {
        if (repository == null || patientId == null) return false;
        int id = patientId.intValue();
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
    @Override
    public Patient getPatientById(int patientId) {
        if (repository == null) return null;
        Patient byDerived = null;
        try {
            byDerived = repository.findByPatientId(patientId);
        } catch (Exception ignored) {
        }
        if (byDerived != null) return byDerived;
        return repository.findById(patientId).orElse(null);
    }
}
 