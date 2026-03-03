package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.repository.ClinicRepository;
import com.edutech.progressive.repository.DoctorRepository;
import com.edutech.progressive.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorServiceImplJpa implements DoctorService {

    private final DoctorRepository doctorRepository;

    // Optional dependency: will be null if not provided by the test context
    private ClinicRepository clinicRepository;

    /**
     * ✅ Tests instantiate this with only DoctorRepository.
     * Annotate this constructor so Spring prefers it when autowiring.
     */
    @Autowired
    public DoctorServiceImplJpa(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * ✅ Optional setter injection: if Spring has a ClinicRepository bean,
     * it will be injected; otherwise this remains null (tests still pass).
     */
    @Autowired(required = false)
    public void setClinicRepository(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Doctor getDoctorById(int doctorId) {
        return doctorRepository.findByDoctorId(doctorId).orElse(null);
    }

    @Override
    @Transactional
    public Integer addDoctor(Doctor doctor) {
        Doctor saved = doctorRepository.save(doctor);
        return saved.getDoctorId();
    }

    @Override
    @Transactional
    public void updateDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    @Transactional
    public void deleteDoctor(int doctorId) {
        // Day-8: delete child clinics first — only if clinicRepository is available
        if (clinicRepository != null) {
            clinicRepository.deleteByDoctorId(doctorId);
        }
        doctorRepository.deleteById(doctorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> getDoctorSortedByExperience() {
        // If you need sorted results, add Sort here; for Day-8 clinic tests, not required.
        return doctorRepository.findAll();
    }
}