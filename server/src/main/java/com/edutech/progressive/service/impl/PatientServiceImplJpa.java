package com.edutech.progressive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.exception.PatientAlreadyExistsException;
import com.edutech.progressive.exception.PatientNotFoundException;
import com.edutech.progressive.repository.BillingRepository;
import com.edutech.progressive.repository.PatientRepository;
import com.edutech.progressive.service.PatientService;

import com.edutech.progressive.dto.PatientDTO;
import com.edutech.progressive.entity.User;
import com.edutech.progressive.exception.DoctorAlreadyExistsException;

import com.edutech.progressive.repository.AppointmentRepository;
import com.edutech.progressive.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Comparator;

@Service
public class PatientServiceImplJpa implements PatientService {

    PatientRepository patientRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public PatientServiceImplJpa(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() throws Exception {
        return patientRepository.findAll();
    }

    @Override
    public Integer addPatient(Patient patient) throws Exception {
        Patient existingPatient = patientRepository.findByEmail(patient.getEmail());
        if (existingPatient != null) {
            throw new PatientAlreadyExistsException("Patient with email " + patient.getEmail() + " already exists");
        }
        return patientRepository.save(patient).getPatientId();
    }

    @Override
    public List<Patient> getAllPatientSortedByName() throws Exception {
        List<Patient> patientList = patientRepository.findAll();
        patientList.sort(Comparator.comparing(Patient::getFullName));
        return patientList;
    }

    @Override
    public void modifyPatientDetails(PatientDTO patientDTO) {
        Patient existingPatient = patientRepository.findByEmail(patientDTO.getEmail());
        User patientUser = userRepository.findByPatientId(patientDTO.getPatientId());
        if (existingPatient != null && existingPatient.getPatientId() != patientDTO.getPatientId()) {
            throw new PatientAlreadyExistsException("Patient with email " + patientDTO.getEmail() + " already exists");
        }
        User user = userRepository.findByUsername(patientDTO.getUsername());
        if (user != null && user.getPatient().getPatientId() != patientDTO.getPatientId()) {
            throw new DoctorAlreadyExistsException("User with username " + patientDTO.getEmail() + " already exists");
        }
        else {
            patientUser.setUsername(patientDTO.getUsername());
        }
        if (!patientUser.getPassword().equals(patientDTO.getPassword())) {
            patientUser.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
        }
        userRepository.save(patientUser);
        Patient patientEntity = new Patient();
        patientEntity.setPatientId(patientDTO.getPatientId());
        patientEntity.setFullName(patientDTO.getFullName());
        patientEntity.setDateOfBirth(patientDTO.getDateOfBirth());
        patientEntity.setEmail(patientDTO.getEmail());
        patientEntity.setContactNumber(patientDTO.getContactNumber());
        patientEntity.setAddress(patientDTO.getAddress());
        patientRepository.save(patientEntity);
    }

    @Override
    public void deletePatient(int patientId) throws Exception {
        if (!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException("Patient with ID " + patientId + " not found for deletion");
        }
        appointmentRepository.deleteByPatientId(patientId);
        billingRepository.deleteByPatientId(patientId);
        userRepository.deleteByPatientId(patientId);
        patientRepository.deleteById(patientId);
    }

    @Override
    public Patient getPatientById(int patientId) throws Exception {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID " + patientId + " not found"));
    }
}
