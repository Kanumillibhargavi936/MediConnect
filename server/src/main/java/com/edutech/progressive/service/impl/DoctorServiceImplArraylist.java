package com.edutech.progressive.service.impl;
 
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.service.DoctorService;
 
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
 
public class DoctorServiceImplArraylist implements DoctorService {
 
    private static final List<Doctor> doctorList = new ArrayList<>();
 
    public void emptyArrayList() {
        doctorList.clear();
    }
 
    @Override
    public List<Doctor> getAllDoctors() throws Exception {
        return new ArrayList<>(doctorList);
    }
 
    @Override
    public Integer addDoctor(Doctor doctor) throws Exception {
        doctorList.add(doctor);
        return doctor.getDoctorId();
    }
 
    @Override
    public List<Doctor> getDoctorSortedByExperience() throws Exception {
        List<Doctor> copy = new ArrayList<>(doctorList);
        copy.sort(Comparator.comparingInt(d -> {
            Integer y = d.getYearsOfExperience();
            return y == null ? 0 : y;
        }));
        return copy;
    }
 
    @Override
    public void updateDoctor(Doctor doctor) throws Exception {
        if (doctor == null) return;
        for (int i = 0; i < doctorList.size(); i++) {
            if (doctorList.get(i).getDoctorId() == doctor.getDoctorId()) {
                Doctor d = doctorList.get(i);
                d.setFullName(doctor.getFullName());
                d.setSpecialty(doctor.getSpecialty());
                d.setContactNumber(doctor.getContactNumber());
                d.setEmail(doctor.getEmail());
                d.setYearsOfExperience(doctor.getYearsOfExperience());
                return;
            }
        }
    }
 
    @Override
    public void deleteDoctor(int doctorId) throws Exception {
        doctorList.removeIf(d -> d.getDoctorId() == doctorId);
    }
 
    @Override
    public Doctor getDoctorById(int doctorId) throws Exception {
        for (Doctor d : doctorList) {
            if (d.getDoctorId() == doctorId) {
                return d;
            }
        }
        return null;
    }
}