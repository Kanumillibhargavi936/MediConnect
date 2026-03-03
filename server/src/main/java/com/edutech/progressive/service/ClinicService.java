package com.edutech.progressive.service;

import com.edutech.progressive.entity.Clinic;

import java.util.List;

public interface ClinicService {

    List<Clinic> getAllClinics();

    Clinic getClinicById(int clinicId);

    Integer addClinic(Clinic clinic);

    void updateClinic(Clinic clinic);

    void deleteClinic(int clinicId);

    List<Clinic> getAllClinicByLocation(String location);

    List<Clinic> getAllClinicByDoctorId(int doctorId);
}