package com.edutech.progressive.service.impl;
 
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.repository.DoctorRepository;
import com.edutech.progressive.service.DoctorService;
import org.springframework.stereotype.Service;
 
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
 
@Service
public class DoctorServiceImplJpa implements DoctorService {
 
    private final DoctorRepository doctorRepository;
 
    public DoctorServiceImplJpa(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
 
    @Override
    public List<Doctor> getAllDoctors() throws Exception {
        try {
            return doctorRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
 
    @Override
    public Integer addDoctor(Doctor doctor) throws Exception {
        try {
            Doctor saved = doctorRepository.save(doctor);
            return saved.getDoctorId();
        } catch (Exception e) {
            return -1;
        }
    }
 
    @Override
    public List<Doctor> getDoctorSortedByExperience() throws Exception {
        try {
            List<Doctor> list = doctorRepository.findAll();
            list.sort(Comparator.comparingInt(d -> d.getYearsOfExperience() == null ? 0 : d.getYearsOfExperience()));
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
 
    @Override
    public void updateDoctor(Doctor doctor) throws Exception {
        try {
            doctorRepository.save(doctor);
        } catch (Exception e) {
            // swallow per spec (void)
        }
    }
 
    @Override
    public void deleteDoctor(int doctorId) throws Exception {
        try {
            doctorRepository.deleteById(doctorId);
        } catch (Exception e) {
            // swallow per spec (void)
        }
    }
 
    @Override
    public Doctor getDoctorById(int doctorId) throws Exception {
        try {
            return doctorRepository.findByDoctorId(doctorId).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}