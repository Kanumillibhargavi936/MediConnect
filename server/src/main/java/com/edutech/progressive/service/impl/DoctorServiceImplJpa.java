package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.exception.DoctorAlreadyExistsException;
import com.edutech.progressive.repository.ClinicRepository;
import com.edutech.progressive.repository.DoctorRepository;
import com.edutech.progressive.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Primary
@Service
public class DoctorServiceImplJpa implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository; 

    public DoctorServiceImplJpa(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
        this.clinicRepository = null;
    }

    @Autowired
    public DoctorServiceImplJpa(DoctorRepository doctorRepository,
                                ClinicRepository clinicRepository) {
        this.doctorRepository = doctorRepository;
        this.clinicRepository = clinicRepository;
    }

    @Override
    public List<Doctor> getAllDoctors() throws Exception {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(int doctorId) throws Exception {
        return doctorRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new Exception("Doctor not found with id: " + doctorId));
    }

    @Override
    public Integer addDoctor(Doctor doctor) throws Exception {
        if (doctor.getEmail() != null &&
            doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
            throw new DoctorAlreadyExistsException("Doctor already exists with email: " + doctor.getEmail());
        }
        Doctor saved = doctorRepository.save(doctor);
        return saved.getDoctorId();
    }

    @Override
    public List<Doctor> getDoctorSortedByExperience() throws Exception {
        List<Doctor> list = doctorRepository.findAll();
        list.sort(Comparator.comparingInt(d -> d.getYearsOfExperience() == null ? 0 : d.getYearsOfExperience()));
        return list;
    }

    @Override
    public void updateDoctor(Doctor doctor) throws Exception {
        if (doctor.getDoctorId() == null || doctor.getDoctorId() <= 0) {
            throw new Exception("Doctor id is required for update");
        }
        Doctor existing = doctorRepository.findByDoctorId(doctor.getDoctorId())
                .orElseThrow(() -> new Exception("Doctor not found with id: " + doctor.getDoctorId()));

        if (doctor.getEmail() != null && !doctor.getEmail().equals(existing.getEmail())) {
            if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
                throw new DoctorAlreadyExistsException("Another doctor already exists with email: " + doctor.getEmail());
            }
            existing.setEmail(doctor.getEmail());
        }

        existing.setFullName(doctor.getFullName());
        existing.setSpecialty(doctor.getSpecialty());
        existing.setContactNumber(doctor.getContactNumber());
        existing.setYearsOfExperience(doctor.getYearsOfExperience());

        doctorRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteDoctor(int doctorId) throws Exception {
        Doctor doctor = doctorRepository.findByDoctorId(doctorId).orElse(null);
        if (doctor == null) return;

        if (clinicRepository != null) {
            clinicRepository.deleteByDoctorId(doctorId);
        }
        doctorRepository.delete(doctor);
    }
}