package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.repository.ClinicRepository;
import com.edutech.progressive.service.ClinicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClinicServiceImplJpa implements ClinicService {

    private final ClinicRepository clinicRepository;

    public ClinicServiceImplJpa(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Clinic getClinicById(int clinicId) {
        // Day-8 compatibility: return null if not found
        return clinicRepository.findByClinicId(clinicId).orElse(null);
    }

    @Override
    @Transactional
    public Integer addClinic(Clinic clinic) {
        Clinic saved = clinicRepository.save(clinic);
        return saved.getClinicId();
    }

    @Override
    @Transactional
    public void updateClinic(Clinic clinic) {
        clinicRepository.save(clinic);
    }

    @Override
    @Transactional
    public void deleteClinic(int clinicId) {
        // Idempotent delete (do not throw if not present)
        try {
            clinicRepository.deleteById(clinicId);
        } catch (Exception ignore) {
            // swallow to match controller's behavior
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Clinic> getAllClinicByLocation(String location) {
        return clinicRepository.findAllByLocation(location);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Clinic> getAllClinicByDoctorId(int doctorId) {
        return clinicRepository.findAllByDoctorId(doctorId);
    }
}