package com.edutech.progressive.service;

import com.edutech.progressive.entity.Clinic;

import java.util.List;

public interface ClinicService {

    List<Clinic> getAllClinics() throws Exception;

    Clinic getClinicById(int clinicId) throws Exception;

    Integer addClinic(Clinic clinic) throws Exception;

    void updateClinic(Clinic clinic) throws Exception;

    void deleteClinic(int clinicId) throws Exception;

    List<Clinic> getAllClinicByLocation(String location) throws Exception;

    List<Clinic> getAllClinicByDoctorId(int doctorId) throws Exception;
}