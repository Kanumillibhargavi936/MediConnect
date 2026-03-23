package com.edutech.progressive.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.service.DoctorService;

public class DoctorServiceImplArraylist implements DoctorService {

    private static List<Doctor>doctorList = new ArrayList<>();

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorList;
    }

    @Override
    public Integer addDoctor(Doctor doctor) {
        doctorList.add(doctor);
        return doctor.getDoctorId();
    }

    @Override
    public List<Doctor> getDoctorSortedByExperience() {
        List<Doctor> sortedDoctors = doctorList;
        sortedDoctors.sort(Comparator.comparing(Doctor::getYearsOfExperience));
        return sortedDoctors;
    }
    @Override
    public void emptyArrayList()
    {
        doctorList=new ArrayList<>();
    }

}