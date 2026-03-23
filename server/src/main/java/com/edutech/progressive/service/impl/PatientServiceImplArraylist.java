package com.edutech.progressive.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.PatientService;

public class PatientServiceImplArraylist implements PatientService {

    private static List<Patient>patientList=new ArrayList<>();

    @Override
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientList);
    }

    @Override
    public Integer addPatient(Patient patient) {
        patientList.add(patient);
        return patientList.size();
    }

    @Override
    public List<Patient> getAllPatientSortedByName() {
        List<Patient> sortedPatients = patientList;
        sortedPatients.sort(Comparator.comparing(Patient::getFullName));
        return sortedPatients;
    }

    @Override
    public void emptyArrayList()
    {
        patientList=new ArrayList<>();
    }




}
